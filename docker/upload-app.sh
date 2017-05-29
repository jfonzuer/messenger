#!/usr/bin/env bash
scp -r back ubuntu@dominapp.com:~/docker/back
scp -r couchdb ubuntu@dominapp.com:~/docker/couchdb
scp -r mysql ubuntu@dominapp.com:~/docker/mysql
scp -r properties ubuntu@dominapp.com:~/docker/properties
scp *.sh ubuntu@dominapp.com:~/docker/
scp docker-compose.yml ubuntu@dominapp.com:~/docker/