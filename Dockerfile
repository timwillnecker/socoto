FROM openjdk:11-jre

ENV TZ=Europe/Amsterdam
ENV LANG=de_DE.UTF-8

MAINTAINER Tim Willnecker

ADD target/*.jar service.jar

EXPOSE 8080

ENTRYPOINT java -jar service.jar --spring.profiles.active=sba --server.port=8080
