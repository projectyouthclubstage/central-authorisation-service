FROM arm32v7/openjdk:11.0.3-slim-stretch
MAINTAINER Sascha Deeg <sascha.deeg@gmail.com>
USER root
RUN apt-get update
RUN apt-get -y install curl
COPY ./target/central-authorisation-service-*.jar /root/demo.jar
EXPOSE 8080
HEALTHCHECK CMD curl -f http://localhost:8080/actuator/health || exit 1;
CMD java -Dspring.profiles.active=docker  -jar /root/demo.jar