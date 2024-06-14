FROM openjdk:17-jdk-alpine
WORKDIR /jwt/token/path
ADD target/SpringJWTToken-0.0.1-SNAPSHOT.jar jwt.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","/jwt/token/path/jwt.jar" ]