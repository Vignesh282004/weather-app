FROM openjdk:17-jdk-slim
WORKDIR /app
EXPOSE 8080
COPY target/spring-weather-0.0.1-SNAPSHOT.jar app.jar
MAINTAINER vignesh282004@gmail.com
CMD ["java","-jar","app.jar"] 
