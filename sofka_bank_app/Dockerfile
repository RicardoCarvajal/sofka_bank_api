FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app
COPY . /app
RUN mvn dependency:go-offline
RUN mvn package

FROM eclipse-temurin:17-jdk-jammy

COPY --from=build /app/target/Cuenta_Bancaria_Web-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app

CMD [ "java", "-jar", "/app/app.jar" ]
