# LUNCHROOM<br/>backend REST API
## Technology stack:
- Java 8
- Kotlin 1.3.50
- SpringBoot 2.2.1.RELEASE
- JUnit 5
- Apache Maven 3.6.2
- Docker
## For build and deploy execute _run.sh_ script<br/>Use:
- -it : run integration tests
- -b : rebuild app
- -ut : build with unit tests
- -d : deploy with docker (port 48080)
## Or manually run:
- `mvn clean package`
- `docker-compose up`