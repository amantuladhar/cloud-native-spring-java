# TODO
- [ ] Reduce inner loop for local dev
  - https://paketo.io/docs/howto/java/#enable-process-reloading
  - Spring devtools

# Kubernetes Commands
- `kubectl create deployment <deployment-name> --image=<image-name>`
- `kubectl get deployment`
- `kubectl get pod`
- `kubectl logs deployment/<deployment-name>`
- `kubectl expose deployment <deployment-name> --type=LoadBalancer --port=8080`
- `kubectl port-forward service/<service-name> <host-port>:<container-port>`
- `kubectl get service`


# Security Vulnerabilities Tool
- https://github.com/anchore/grype

# Tools
- https://docs.tilt.dev/install.html

