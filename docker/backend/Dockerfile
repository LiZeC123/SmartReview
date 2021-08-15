FROM  openjdk:11.0.10-jre-slim

WORKDIR /app
COPY target/smart-review-1.0.0.jar .

ENTRYPOINT [ "java", "-jar",  "smart-review-1.0.0.jar", "--spring.profiles.active=prod"]