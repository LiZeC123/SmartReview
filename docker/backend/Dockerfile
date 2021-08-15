FROM maven:3.8-openjdk-11-slim AS build
WORKDIR /app
COPY app/pom.xml .
RUN mvn dependency:go-offline
COPY app .
RUN mvn package

# -------------------------------

FROM openjdk:11.0.10-jre-slim
COPY --from=build /app/target/smart-review-1.0.0.jar /app/
WORKDIR /app
ENTRYPOINT [ "java", "-jar", "smart-review-1.0.0.jar", "--spring.profiles.active=prod" ]
