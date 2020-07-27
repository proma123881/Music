FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /target/music-api-0.0.1-SNAPSHOT.jar .
ENTRYPOINT [ "java","-jar","music-api-0.0.1-SNAPSHOT.jar" ]