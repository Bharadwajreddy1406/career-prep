
---

Overview https://docs.nvidia.com/openshell/about/overview
Overall working : https://docs.nvidia.com/openshell/about/how-it-works

supervisor https://docs.nvidia.com/openshell/about/how-it-works#supervisor-protection-layers


Static controls such as filesystem and process isolation are established at sandbox start and require sandbox recreation to change. Dynamic controls such as network policy, credential delivery, and inference routing can refresh over the live gateway-supervisor session.

OpenShell supports several local compute drivers. Package-managed gateways leave the driver unset by default so the gateway can auto-detect an available driver. Set `compute_drivers` in the gateway TOML when you need to pin a specific driver.

https://docs.nvidia.com/openshell/about/installation#supported-compute-drivers


Linux packages require glibc 2.28 or newer. The installer checks libc before downloading packages and exits with an error on older glibc versions, Alpine, musl-based distributions, or unknown libc environments.


The Linux user service listens on `https://127.0.0.1:17670`, starts from built-in defaults, and generates a local mTLS bundle before the gateway starts. Create `~/.config/openshell/gateway.toml` only when you need to override those defaults.

The CLI reads the client bundle from `~/.config/openshell/gateways/openshell/mtls/`.

The installer starts the service for you. Use systemd user commands when you need to inspect, restart, or stop the gateway service:


### K8s Deployment for openshell

https://docs.nvidia.com/openshell/about/installation#kubernetes