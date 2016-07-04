#!/bin/bash
cd target
export DOCKER_HOST=tcp://10.0.1.237:2375
export DOCKER_REPO=wddocker.mapbar.com
docker build -t $DOCKER_REPO/$1.$2:$3 .
docker tag -f $DOCKER_REPO/$1.$2:$3 $DOCKER_REPO/$1.$2:latest
docker push $DOCKER_REPO/$1.$2