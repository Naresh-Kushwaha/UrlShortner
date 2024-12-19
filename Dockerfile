FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/UrlShortner-0.0.1-SNAPSHOT.jar UrlShortner.jar
EXPOSE 8080
<<<<<<< HEAD
ENTRYPOINT ["java","-jar","UrlShortner.jar"]
=======
ENTRYPOINT ["java","-jar","UrlShortner.jar"]
>>>>>>> be9e55b32aa6a0f53077c5905801460b1893acb9
