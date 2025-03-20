
- `https://github.com/kubernetes-sigs/metrics-server`
- `kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml`
- `kubectl patch deployment metrics-server -n kube-system --type='json' -p='[{"op": "add", "path": "/spec/template/spec/containers/0/args/-", "value": "--kubelet-insecure-tls"}]'`
- `kubectl rollout restart deployment metrics-server -n kube-system`

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


- `./kcadm.sh config credentials --server http://localhost:8080/auth --realm master --user admin --password admin
- `./kc.sh export --realm <realm_name> --users REALM_FILE --file <output_file_name>.json`
