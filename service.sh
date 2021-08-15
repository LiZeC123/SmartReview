#!/usr/bin/env bash

AppName=target/smart-review-0.0.1-SNAPSHOT.jar

function init {
  docker build -t maven -f docker/maven/Dockerfile .
  docker build -t smart-review-mysql -f docker/mysql/Dockerfile .
}

function compileService() {
  # 编译的时候, 测试阶段可能启动spring, 会导致端口冲突?
  docker run 
  mvn clean package
}

function runService() {
  echo "Run Service In Background"
  nohup java -jar $AppName --spring.profiles.active=prod >smart-review.log 2>&1 &
  exit
}

stopService() {
  echo "Kill Current Service"
  kill "$(jps -l | grep $AppName | awk '{print $1}')"
}

if [ "$1"x == "start"x ]; then
  compileService
  runService
elif [ "$1"x == "compile"x ]; then
  compileService
elif [ "$1"x == "run"x ]; then
  runService
elif [ "$1"x == "stop"x ]; then
  stopService
elif [ "$1"x == "restart"x ]; then
  stopService
  compileService
  runService
else
  echo "无效的参数: $1"
  echo ""
  echo "用法: ./service [参数]"
  echo "参数可以选择以下值:"
  echo "start     编译并启动项目"
  echo "stop      停止项目"
  echo "restart   重启项目"
  echo "compile   只编译项目"
  echo "run       直接运行项目"
  echo ""
fi
