# IHM SpringBoot 2 Microservice Demo
Spring Boot REST service

Run SpringBoot application and access http://localhost:8080/swagger-ui.html (see below for how to run). This will allow running endpoints from the Swagger API browser. There are some sample Advertisers for viewing but can create new ones through the API browser as well. 
Example:
```
curl -X POST http://localhost:8080/api/advertiser --data '{"name":"Advertiser ABC", "contactName":"Johnny Test", "creditLimit":10000}' -H "Content-Type: application/json"
```
> {"id":14,"name":"Advertiser ABC","contactName":"Johnny Test","creditLimt":10000}


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
Build will run tests and coverage verification and reporting (set at 90%, report in /build/reports/jacoco/test/html/index.html). Note most relevant source files are in `/src/main/java` and `/src/test/java`. The gradle build file, Dockerfile, lombok config and this readme file are in the root directory.

```
git clone https://github.com/planckscale/ihm-rest-boot.git
cd ihm-rest-boot.git
./gradlew clean build
./gradlew bootRun
```

## Run as excutable jar (standalone)
```
./gradlew bootJar
java -jar build/libs/boot-rest-demo-0.0.1-SNAPSHOT.jar
```

## Run as Docker container
```
docker build . -t boot-rest-demo-0.0.1
docker run -p 8080:8080 -p 8090:8090 boot-rest-demo-0.0.1:latest
```
