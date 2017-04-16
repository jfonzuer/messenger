#!/bin/bash
set -xe

DIR="$(pwd)"
PATH_TO_COUCHDB=$DIR"/couchdb"

# on check si le network n'existe pas
EXISTING_NETWORK=$(docker network inspect messenger-network || true)
if [ "$EXISTING_NETWORK" == "[]" ]; then
	echo "Creating messenger-network"
	docker network create messenger-network
fi

# on check si le docker messenger est en train de run
EXISTING=$(docker inspect messenger-couchdb || true)

# si le container exist
if [ "$EXISTING" != "[]" ]; then
  echo "Stopping and removing docker messenger-couchdb"
  docker stop messenger-couchdb
  docker rm messenger-couchdb
fi

if [[ "$(docker images -q jfonzuer/messenger-couchdb 2> /dev/null)" != "" ]]; then
  echo "Deleting existing image jfonzuer/messenger-couchdb"
  docker rmi jfonzuer/messenger-couchdb
fi

cd $PATH_TO_COUCHDB
echo "build docker image from Dockerfile"
docker build -t jfonzuer/messenger-couchdb .
#/usr/local/etc/couchdb/
#/usr/var/local/lib/couchdb
docker run --name=messenger-couchdb -p 5985:5984 --net=messenger-network -v $DIR/data/couchdb:/usr/local/var/lib/couchdb -e COUCHDB_USER=root -e COUCHDB_PASSWORD=password -d jfonzuer/messenger-couchdb
