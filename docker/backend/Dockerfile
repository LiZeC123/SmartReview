FROM maven:3.8-openjdk-11-slim AS build
WORKDIR /app
COPY app/pom.xml .
RUN mvn dependency:go-offline
COPY app .
RUN mvn package -Dmaven.test.skip=true

# -------------------------------

FROM openjdk:11.0.10-jre-slim
WORKDIR /app

RUN apt-get update && \
    apt-get install -y sqlite && \
    rm -rf /var/lib/apt/lists/* 

COPY --from=build /app/target/smart-review-1.0.0.jar /app/

# 由于服务器是资源有限, 因此采取消耗更小的垃圾回收算法
ENTRYPOINT [ "java", "-XX:+UseSerialGC", "-jar", "smart-review-1.0.0.jar", "--spring.profiles.active=prod" ]
