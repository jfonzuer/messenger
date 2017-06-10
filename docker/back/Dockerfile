FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD app.jar app.jar
ADD application.properties application.properties
ADD logback.xml logback.xml
RUN sh -c 'touch /app.jar'
RUN apk add -U tzdata
RUN cp /usr/share/zoneinfo/Europe/Paris /etc/localtime
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]