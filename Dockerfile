FROM eclipse-temurin:17-jdk-alpine
VOLUME /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]