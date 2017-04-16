#!/usr/bin/env bash
set -xe
DIR="$(pwd)"
PATH_TO_MYSQL=$DIR"/mysql"

# on check si le network n'existe pas
EXISTING_NETWORK=$(docker network inspect messenger-network || true)
if [ "$EXISTING_NETWORK" == "[]" ]; then
	echo "Creating messenger-network"
	docker network create messenger-network
fi

# on check si le docker messenger est en train de run
EXISTING=$(docker inspect messenger-mysql || true)

# si le container exist
if [ "$EXISTING" != "[]" ]; then
  echo "Stopping and removing docker messenger-mysql"
  docker stop messenger-mysql
  docker rm messenger-mysql
fi

if [[ "$(docker images -q jfonzuer/messenger-mysql 2> /dev/null)" != "" ]]; then
  echo "Deleting existing image jfonzuer/messenger-mysql"
  docker rmi jfonzuer/messenger-mysql
fi

cd $PATH_TO_MYSQL
echo "build docker image from Dockerfile"
docker build -t jfonzuer/messenger-mysql .
docker run --name=messenger-mysql --net=messenger-network -p 3307:3306 -v $DIR/data/mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=messenger  -d jfonzuer/messenger-mysql
