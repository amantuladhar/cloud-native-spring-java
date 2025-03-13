#



# Push docker image to registry
- Build Image
```
./gradlew clean build bootBuildImage 
```

- Build and push
```sh
./gradlew bootBuildImage \
--imageName ghcr.io/<username>/catalog-service \
--publishImage \
-PregistryUrl=ghcr.io \
-PregistryUsername=<username> \
-PregistryToken=<token>
```
