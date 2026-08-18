
---

Extract the supervisor binary to the host once, then start the gateway:
```shell
mkdir -p ~/openshell/supervisor
docker create --name tmp-supervisor ghcr.io/nvidia/openshell/supervisor:latest
docker cp tmp-supervisor:/openshell-sandbox ~/openshell/supervisor/openshell-sandbox
docker rm tmp-supervisor
chmod +x ~/openshell/supervisor/openshell-sandbox

```


start the gateway
---

```shell
docker run -d \
  --name openshell-gateway \
  --restart unless-stopped \
  --group-add "$(getent group docker | cut -d: -f3)" \
  --add-host host.openshell.internal:host-gateway  \
  -p 127.0.0.1:9090:8080 \
  -v openshell-state:/var/openshell \
  -v ~/openshell/supervisor/openshell-sandbox:/openshell/supervisor/openshell-sandbox:ro \
  -v "$HOME/.docker/desktop/docker.sock:/var/run/docker.sock" \
  -e DOCKER_HOST=unix:///var/run/docker.sock \
  -e OPENSHELL_DRIVERS=docker \
  -e OPENSHELL_GRPC_ENDPOINT=http://host.openshell.internal:8080 \
  -e OPENSHELL_DOCKER_SUPERVISOR_BIN=/openshell/supervisor/openshell-sandbox \
  -e OPENSHELL_DB_URL=sqlite:/var/openshell/openshell.db \
  -e OPENSHELL_DISABLE_TLS=true \
  ghcr.io/nvidia/openshell/gateway:latest

```

this is not the NVIDIA Docs command directly
changes made are :
-  ` -v ~/openshell/supervisor/openshell-sandbox:/openshell/supervisor/openshell-sandbox:ro \`  docs command had `~` at the container path beginning which will not be resolved by docker if it's absolute, so that ~ was removed
- with the line `--group-add docker` in docs command we'd Add the process inside the container to the group named `docker`
- But the OpenShell gateway image **doesn't have a group named `docker`** in its `/etc/group` file.

So Docker fails before the container even starts.

So Instead of the **group name**, we pass the **numeric GID** of the Docker group on your host.

to get it 
```shell
getent group docker
```
we'll get something like this `docker:x:983:bharadwaj`
we care about 983 there.

so we've replaced that line there

- and lastly the `OPENSHELL_DOCKER_SUPERVISOR_BIN`, as we've removed the ~ before, we removed here too.


---

register the gateway with the cli

