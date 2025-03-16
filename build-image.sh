#!/bin/sh

cd catalogservice
./gradlew clean build bootBuildImage
cd ..

cd config-service
./gradlew clean build bootBuildImage
cd ..

cd dispatcher-service
./gradlew clean build bootBuildImage
cd ..

cd edge-service
./gradlew clean build bootBuildImage
cd ..

cd orders-service
./gradlew clean build bootBuildImage
cd ..
