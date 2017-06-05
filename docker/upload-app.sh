#!/usr/bin/env bash
./package-app.sh

ssh ubuntu@dominapp.com 'docker-compose -f docker/docker-compose.yml down'
ssh ubuntu@dominapp.com 'sudo rm -rf docker/*'
ssh ubuntu@dominapp.com 'docker rmi docker_mysql docker_back docker_couchdb'

scp -r back ubuntu@dominapp.com:~/docker/
scp -r couchdb ubuntu@dominapp.com:~/docker/
scp -r mysql ubuntu@dominapp.com:~/docker/
scp -r properties ubuntu@dominapp.com:~/docker/
scp *.sh ubuntu@dominapp.com:~/docker/
scp docker-compose.yml ubuntu@dominapp.com:~/docker/

ssh ubuntu@dominapp.com 'docker-compose -f docker/docker-compose.yml up'