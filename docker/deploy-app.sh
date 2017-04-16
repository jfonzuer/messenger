#!/usr/bin/env bash
set -xe
DIR=$(pwd)
PATH_TO_FRONT=$DIR"/../front/"
PATH_TO_BACK=$DIR"/../back/"
PATH_TO_DOCKER=$(pwd)
ROOT=$DIR"/.."

#npm run build
cp $DIR/properties/environment.prod.ts $DIR/../front/src/environments/environment.prod.ts
cd $PATH_TO_FRONT
ng build --prod

# on copie les properties dans /back
cp $PATH_TO_DOCKER/properties/application.properties $DIR/back/
cp $PATH_TO_DOCKER/properties/logback.xml $DIR/back

cd $ROOT
rm -r back/src/main/resources/static
mkdir -p back/src/main/resources/static
cp -r front/dist/* back/src/main/resources/static/
cp -r front/src/assets/bootstrap/fonts/* back/src/main/resources/static/assets/bootstrap/fonts/

# rebuild l'image
cd $PATH_TO_BACK
rm -rf target/
#mvn package
mvn clean install -P prod -DskipTests

cd target/
for f in *.jar
do
	echo "Copying " $f
	cp $f $PATH_TO_DOCKER"/back/app.jar"
done

# on check si le network n'existe pas
EXISTING_NETWORK=$(docker network inspect messenger-network || true)
if [ "$EXISTING_NETWORK" == "[]" ]; then
	echo "Creating messenger-network"
	docker network create messenger-network
fi

# on check si le docker messenger est en train de run
EXISTING=$(docker inspect messenger  || true)

# si le container exist
if [ "$EXISTING" != "[]" ]; then
  echo "Stopping and removing docker messenger"
  docker stop messenger
  docker rm messenger
fi

if [[ "$(docker images -q jfonzuer/messenger 2> /dev/null)" != "" ]]; then
  echo "Deleting existing image jfonzuer/messenger"
  docker rmi jfonzuer/messenger
fi

cd $PATH_TO_DOCKER"/back"
echo "build docker image from Dockerfile"
docker build -t jfonzuer/messenger .
docker run -p 8080:8080 --net=messenger-network --name=messenger -t jfonzuer/messenger
