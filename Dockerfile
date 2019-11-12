FROM openjdk:8
MAINTAINER mateuszbajdak@gmail.com
ENV SPRING_PROFILES_ACTIVE=docker
RUN mkdir app
WORKDIR app
COPY ./target/lunchroom-0.0.1-SNAPSHOT.jar .
CMD [ "java", "-jar", "lunchroom-0.0.1-SNAPSHOT.jar"]