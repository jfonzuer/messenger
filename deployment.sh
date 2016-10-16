DIR="$(pwd)"
echo $DIR
PATH_TO_FRONT=$DIR"/front/"
echo $PATH_TO_FRONT
PATH_TO_BACK=$DIR"/back/"
echo $PATH_TO_BACK
PATH_TO_DOCKER=$DIR"/docker/"

cd $PATH_TO_FRONT
#npm run build

cd $DIR
rm -r back/src/main/resources/static/*
cp -r front/dist/* back/src/main/resources/static/

# on check si le network n'existe pas
EXISTING_NETWORK=$(docker network inspect messenger-network 2> /dev/null)
if [ "$EXISTING_NETWORK" == "[]" ]; then
	echo "Creating messenger-network"
	docker network create messenger-network
fi

# on check si le docker messenger-messenger est en train de run
EXISTING=$(docker inspect messenger-messenger 2> /dev/null)

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

# rebuild l'image
cd back/
rm -r target/
mvn package

cd target/
for f in *.jar
do
	echo "Copying " $f
	cp $f $PATH_TO_DOCKER"/back/app.jar"
done

cd $PATH_TO_DOCKER"/back"
echo "build docker image from Dockerfile"
docker build -t jfonzuer/messenger .
docker run -p 8080:8080 --net=messenger-network --name=messenger -t jfonzuer/messenger
