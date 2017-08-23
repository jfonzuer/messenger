#!/usr/bin/env bash
set -xe

## Check if script is run as root
#if [ "$EUID" -ne 0 ]; then
#    echo -e $WARN "Usage of this script: sudo -E env "PATH=$PATH" ./deploy-dev.sh"
#    exit 1
#fi

DIR=$(pwd)
PATH_TO_FRONT=$DIR"/../front/"
PATH_TO_BACK=$DIR"/../back/"
PATH_TO_STATIC=$DIR"/../static/"
PATH_TO_DOCKER=$(pwd)
ROOT=$DIR"/.."

cd $PATH_TO_DOCKER/properties
find . -type f -exec sed -i 's/dominapp.com/dominapp-dev.com/g' {} \;
find . -type f -exec sed -i 's/@dominapp-dev.com/@dominapp.com/g' {} \;

find . -type f -exec sed -i 's/spring.profiles.active=prod/spring.profiles.active=preprod/g' {} \;
find . -type f -exec sed -i 's/production: true/production: false/g' {} \;

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
cp front/dist/index.html back/src/main/resources/templates/app/index.html
cp front/src/styles.css back/src/main/resources/static/
cp back/src/main/resources/robots.txt back/src/main/resources/static/robots.txt

# rebuild l'image
cd $PATH_TO_BACK
rm -rf target/
mvn clean package -P prod -DskipTests
cd target/
cp *.jar $PATH_TO_DOCKER"/back/app.jar"

# rebuild static
cd $PATH_TO_STATIC
rm -rf target/
mvn clean package -DskipTests
cd target/
cp *.jar $PATH_TO_DOCKER"/static/static.jar"

# clean datas
cd $DIR
sudo rm -rf data/couchdb/*
sudo rm -rf data/mysql/*

sudo docker-compose down
sudo docker rmi docker_back docker_couchdb docker_mysql docker_static
sudo docker-compose up
