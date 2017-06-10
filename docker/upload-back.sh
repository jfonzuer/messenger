#!/usr/bin/env bash
./package-app.sh

scp -r back ubuntu@dominapp.com:~/docker/
#ssh ubuntu@dominapp.com 'docker-compose -f docker/docker-compose.yml down'
#ssh ubuntu@dominapp.com 'docker rmi docker_back'
#ssh ubuntu@dominapp.com 'docker-compose -f docker/docker-compose.yml up'