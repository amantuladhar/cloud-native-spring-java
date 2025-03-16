#!/bin/sh

# List of project directories
PROJECTS="catalogservice config-service dispatcher-service edge-service orders-service"

for project in $PROJECTS; do
    cd $project || exit
    ./gradlew clean build bootBuildImage
    cd ..
done
