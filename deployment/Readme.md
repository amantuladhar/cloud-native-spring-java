
- `minikube start --cpus 2 --memory 4g --driver docker --profile cnative`
- `kubectl get nodes`
- `kubectl config get-contexts`
- `kubectl config current-context`
- `kubectl config use-context cnative`
- `minikube stop --profile cnative`
- `minikube delete --profile cnative`
- `kubectl logs deployments/<deployment-name> -n <namespace>`
