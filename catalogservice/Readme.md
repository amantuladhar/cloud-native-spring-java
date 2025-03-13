#



# Push docker image to registry
```sh
./gradlew bootBuildImage \
--imageName ghcr.io/<username>/catalog-service \
--publishImage \
-PregistryUrl=ghcr.io \
-PregistryUsername=<username> \
-PregistryToken=<token>
```
