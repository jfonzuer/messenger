#!/usr/bin/env bash
set -xe
source 'config.sh'

rm -rf ./data/couchdb/*
rm -rf ./data/mysql/*
rm -rf ./logs/messenger/*

scp -r ./docker/back ubuntu@$DOMINAPP_HOST:~/docker/back
scp -r ./docker/couchdb ubuntu@$DOMINAPP_HOST:~/docker/couchdb
scp -r ./docker/data ubuntu@$DOMINAPP_HOST:~/docker/data
scp -r ./docker/mysql ubuntu@$DOMINAPP_HOST:~/docker/mysql
scp docker-compose.yml ubuntu@$DOMINAPP_HOST:~/
