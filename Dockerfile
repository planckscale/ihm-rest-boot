FROM openjdk:8-jdk-alpine
EXPOSE 8080 8090
ADD /build/libs/boot-rest-demo-0.0.1-SNAPSHOT.jar boot-rest-demo-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/boot-rest-demo-0.0.1.jar" ]