FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD="123456" MYSQL_ROOT_HOST=%

WORKDIR /app

# 将初始化脚本复制到此位置, 镜像第一次启动时自动执行
# 详情见文档 Initializing a fresh instance 章节
# https://hub.docker.com/_/mysql
COPY sql/ /docker-entrypoint-initdb.d/
