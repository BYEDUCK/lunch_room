FROM maven:3.6.3-jdk-11
MAINTAINER mateuszbajdak@gmail.com
ENV SPRING_PROFILES_ACTIVE=docker
RUN mkdir app
WORKDIR app
COPY . .
RUN mvn clean package -DskipTests
CMD [ "java", "-jar", "./target/lunchroom-0.0.1-SNAPSHOT.jar"]