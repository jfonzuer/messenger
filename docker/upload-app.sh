#!/usr/bin/env bash
./package-app.sh

ssh ubuntu@dominapp.com 'rm -rf /home/ubuntu/docker/*'
ssh ubuntu@dominapp.com 'cd docker/ && docker-compose down'
ssh ubuntu@dominapp.com 'docker rmi docker_mysql docker_back docker_couchdb'

scp -r back ubuntu@dominapp.com:~/docker/back
scp -r couchdb ubuntu@dominapp.com:~/docker/couchdb
scp -r mysql ubuntu@dominapp.com:~/docker/mysql
scp -r properties ubuntu@dominapp.com:~/docker/properties
scp *.sh ubuntu@dominapp.com:~/docker/
scp docker-compose.yml ubuntu@dominapp.com:~/docker/

ssh ubuntu@dominapp.com 'cd docker/ && docker-compose up &'