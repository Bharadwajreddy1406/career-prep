
---

### 01 Step by Step Process to get it worked on local


1. **Pulled `docker-compose.yml` and `gateway.toml` from the official NVIDIA `OpenShell` repo** (`deploy/docker/`), ran it locally as-is on port 8080.
2. **Hit the JWT error first** — `failed to read sandbox JWT signing key from /etc/openshell/jwt/signing.pem`. The default compose/TOML don't ship any signing key material — you generate it yourself:
    - Generated an ed25519 keypair + a `kid` file (`openssl genpkey`, `openssl pkey -pubout`, `uuidgen`).
    - Put them at `~/.config/openshell/jwt/`.
    - Bind-mounted that directory into the container at `/etc/openshell/jwt`.
    - Added a `[openshell.gateway.gateway_jwt]` block to `gateway.toml` pointing at those paths explicitly.
3. **Also added `extra_hosts`** for `host.docker.internal`/`host.openshell.internal` → `host-gateway`, since you confirmed via `docker context ls` that you're on plain Docker Engine (`default` context, `/var/run/docker.sock`), not Docker Desktop — Desktop provides that DNS alias automatically, plain Engine doesn't.
4. Gateway came up clean on 8080, no more JWT crash-loop.
5. **You then said you wanted 8080 off** (something local was conflicting with it), so you asked to move to 9090.
6. First attempt: just changed `bind_address`/`grpc_endpoint` in the TOML to 9090 and the compose `ports:` to `9090:9090` — but the gateway kept logging `bind=0.0.0.0:8080` regardless. Root cause: **the image's default `CMD` (`--bind-address 0.0.0.0 --port 8080`) beats the TOML**, and `command: []` (meant to clear that CMD so TOML takes over) wasn't actually taking effect on the running container even after `down`/`up` — a stale-container issue, confirmed via `docker inspect ... Config.Cmd`.
7. As a stopgap, you asked to just **forward host 9090 → container 8080** (asymmetric mapping) instead of fixing the CMD problem.
8. **Registered the gateway** at `:9090` — `openshell status` connected, but showed `Authentication: Failed (missing authorization header)`. Fixed by adding `allow_unauthenticated_users = true` under `[openshell.gateway.auth]` in the TOML (needed because `disable_tls = true` means there's no mTLS/OIDC identity for the gateway to check against).
9. Tried `sandbox create` — container got created but **crash-looped**: `Policy fetch failed, retrying`, gRPC `connection was not ready` / `broken pipe`. Root cause: the sandbox supervisor calls back to `host.openshell.internal:<gateway's internal bind port>` — which was still **8080** — but you'd only published 8080→nothing (it was remapped asymmetrically to 9090), so nothing was actually listening on the address the sandbox was trying to reach.
10. Fixed by **explicitly setting `command: ["--bind-address", "0.0.0.0", "--port", "9090"]`** in compose (bypassing the broken `command: []` TOML-handoff entirely) — confirmed via `docker inspect` that the container's actual `Cmd` and the logged `bind=` address both now say 9090.
11. Matched the host `ports:` mapping to `9090:9090` (symmetric) and `grpc_endpoint = "http://host.openshell.internal:9090"` in the TOML.
12. Re-registered the gateway at `:9090`, `openshell status` → `Connected`.
13. `sandbox create --name deepagent-sandbox --keep` → succeeded, dropped into a working shell.


## Command Log, what did i run 
---


## JWT signing key generation (fix #1)

```bash
mkdir -p ~/.config/openshell/jwt
openssl genpkey -algorithm ed25519 -out ~/.config/openshell/jwt/signing.pem
openssl pkey -in ~/.config/openshell/jwt/signing.pem -pubout -out ~/.config/openshell/jwt/public.pem
uuidgen > ~/.config/openshell/jwt/kid
ls -l ~/.config/openshell/jwt   # verify: signing.pem, public.pem, kid all present
```

## Bringing the gateway up / down

```bash
docker compose up -d
docker compose down
docker compose up -d --force-recreate    # use when TOML/compose edits don't seem to apply
docker compose rm -f gateway             # nuke a specific stuck container if down alone isn't enough
```

## Checking what's actually running (the core diagnostic loop)

```bash
docker compose ps                                              # STATUS: Up vs Restarting
docker compose logs -f gateway                                 # live gateway logs
docker compose logs --tail=100 gateway                         # last N lines
docker inspect server-gateway-1 --format '{{.Config.Cmd}}'      # what CMD the container actually has
docker inspect server-gateway-1 --format '{{.Created}}'         # confirm it's actually a fresh container
docker compose config | grep -A2 "command:"                     # confirm resolved compose config
docker compose config | grep -A3 ports:                         # confirm resolved port mapping
```

## Network/port checks

```bash
ss -tlnp | grep -E '127.0.0.1:9090|127.0.0.1:8081'   # is anything actually listening
sudo lsof -i :9090                                    # alternative if ss doesn't show it
curl -sv http://127.0.0.1:9090 2>&1 | head -10        # confirm TCP-reachable
curl -sf http://127.0.0.1:8081/healthz                # health endpoint check
docker context ls                                     # confirm default vs desktop-linux
docker info --format '{{.OperatingSystem}}'
```

## Sandbox-side network diagnosis (used when sandboxes were crash-looping)

```bash
docker ps -a --filter "name=openshell"                          # find sandbox container
docker logs <sandbox-container-id> --tail 100                   # sandbox supervisor logs
docker exec <sandbox-container-id> getent hosts host.openshell.internal   # what IP it resolves to
docker exec <sandbox-container-id> sh -c "curl -sv http://<GW_IP>:PORT/ 2>&1 | head -20"
docker inspect server-gateway-1 --format '{{json .NetworkSettings.Networks}}'
docker inspect <sandbox-container-id> --format '{{json .NetworkSettings.Networks}}'
```

## CLI gateway registration / status

```bash
openshell gateway add http://127.0.0.1:9090 --local --name local
openshell gateway remove local          # used before re-adding after port changes
openshell gateway list                  # confirm which one is active
openshell status                        # Connected / Authentication check
```

## Provider + sandbox lifecycle

```bash
ANTHROPIC_API_KEY=sk-ant-... openshell provider create --name anthropic --type anthropic --from-existing
openshell provider create --name openai --type openai --from-existing
openshell provider list

openshell sandbox create --name deepagent-sandbox --keep
openshell sandbox list                  # phase: Provisioning → Ready
openshell sandbox delete deepagent-sandbox --force
openshell logs deepagent-sandbox --tail --source sandbox
```

## The two config edits that actually mattered

**`gateway.toml`** — added JWT key paths and the auth escape hatch:

```toml
[openshell.gateway.auth]
allow_unauthenticated_users = true

[openshell.gateway.gateway_jwt]
signing_key_path = "/etc/openshell/jwt/signing.pem"
public_key_path  = "/etc/openshell/jwt/public.pem"
kid_path         = "/etc/openshell/jwt/kid"
gateway_id       = "openshell"
```

**`docker-compose.yml`** — the fix that finally made the port change stick, bypassing the broken `command: []`→TOML handoff:

```yaml
command: ["--bind-address", "0.0.0.0", "--port", "9090"]
extra_hosts:
  - "host.docker.internal:host-gateway"
  - "host.openshell.internal:host-gateway"
ports:
  - "9090:9090"
  - "127.0.0.1:8081:8081"
volumes:
  - type: bind
    source: ${HOME}/.config/openshell/jwt
    target: /etc/openshell/jwt
    read_only: true
```

