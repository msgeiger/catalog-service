#!/usr/bin/env

# java -jar build/libs/simplyadd-0.0.1-SNAPSHOT.jar 
# sudo eval $(minikube docker-env) && ./gradlew clean bootBuildImage
eval $(minikube -p minikube docker-env)
# ./gradlew clean bootBuildImage
./gradlew bootBuildImage 
