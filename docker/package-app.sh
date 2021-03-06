#!/usr/bin/env bash
set -xe
DIR=$(pwd)
PATH_TO_FRONT=$DIR"/../front/"
PATH_TO_BACK=$DIR"/../back/"
PATH_TO_DOCKER=$(pwd)
ROOT=$DIR"/.."

cd $PATH_TO_DOCKER/properties
find . -type f -exec sed -i 's/dominapp-dev.com/dominapp.com/g' {} \;
find . -type f -exec sed -i 's/spring.profiles.active=preprod/spring.profiles.active=prod/g' {} \;
find . -type f -exec sed -i 's/production: false/production: true/g' {} \;

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
for f in *.jar
do
	echo "Copying " $f
	cp $f $PATH_TO_DOCKER"/back/app.jar"
done