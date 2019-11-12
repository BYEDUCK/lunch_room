FROM openjdk:8
MAINTAINER mateuszbajdak@gmail.com
ENV SPRING_PROFILES_ACTIVE=docker
#ENV JASYPT_ENCRYPTOR_PASSWORD=v59cMIuSCBS18yKSvLfKN1xh7
RUN mkdir app
WORKDIR app
COPY ./target/lunchroom-0.0.1-SNAPSHOT.jar .
CMD [ "java", "-jar", "lunchroom-0.0.1-SNAPSHOT.jar"]