# Notification Project

### Prerequisites:

#####

- Java 11+

## Startup process:

### Notification

##### 1 Start infrastructure locally with docker

`comment out unnecessary applications`
`docker-compose up -d`

##### 2 Build project

`mvn clean install`

##### 3 Run

`mvn spring-boot:run -Dspring.profiles.active=local`

### Open application:

#### Security

`u can disable security`
`keycloak.enable set false`
