#!/bin/sh

echo "\n🏴️ Destroying Kubernetes cluster...\n"

minikube stop --profile cnative

minikube delete --profile cnative

echo "\n🏴️ Cluster destroyed\n"
