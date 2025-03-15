
- `https://github.com/kubernetes-sigs/metrics-server`

- `minikube start --cpus 2 --memory 4g --driver docker --profile cnative`
- `kubectl get nodes`
- `kubectl config get-contexts`
- `kubectl config current-context`
- `kubectl config use-context cnative`
- `minikube stop --profile cnative`
- `minikube delete --profile cnative`
- `kubectl logs deployments/<deployment-name> -n <namespace>`
- `minikube image load catalog-service --profile cnative`
- `kubectl port-forward service/catalog-service 9001:80`

- `minikube addons enable ingress --profile cnative`
- `kubectl get all -n ingress-nginx`
- `minikube ip --profile cnative`
- `minikube tunnel --profile cnative`
