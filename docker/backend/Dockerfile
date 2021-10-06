FROM maven:3.8-openjdk-11-slim AS build
WORKDIR /app
COPY app/pom.xml .
RUN mvn dependency:go-offline
COPY app .
RUN mvn package -Dmaven.test.skip=true

# -------------------------------

# 使用JDK运行, 从而包含JDK中的基本工具
# FROM openjdk:11.0.10-jre-slim
FROM openjdk:11.0.10-jdk-buster
WORKDIR /app

RUN apt-get update && \
    apt-get install -y sqlite && \
    rm -rf /var/lib/apt/lists/* 

COPY --from=build /app/target/smart-review-1.0.0.jar /app/

# 由于服务器资源有限, 默认只使用1/4内存, 因此手动指定最大可用内存
ENTRYPOINT [ "java", "-Xmx300M", "-jar", "smart-review-1.0.0.jar", "--spring.profiles.active=prod" ]
