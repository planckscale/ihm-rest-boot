# IHM SpringBoot 2 Microservice Demo
Spring Boot REST service

Run SpringBoot application and access http://localhost:8080/swagger-ui.html (see below for how to run). This will allow running endpoints from the Swagger API browser. There are some sample Advertisers for viewing but can create new ones through the API browser as well. 

Actuator monitoring is running on port 8090. 
Examples:
```
curl http://localhost:8090/actuator/health
```
> {"status":"UP"}
```
curl http://localhost:8090/actuator/info
```
> {"app":{"name":"IHM Spring Boot 2 REST Service","description":"iHeartMedia Demo Project"},"java-vendor":"Oracle Corporation"}


## Build and run (unix like system)

```
git clone https://github.com/planckscale/ihm-rest-boot.git
cd ihm-rest-boot.git
./gradlew bootRun
```

## Excutable jar can be run as well (standalone)
```
./gradlew bootJar
java -jar build/libs/boot-rest-demo-0.0.1-SNAPSHOT.jar
```
