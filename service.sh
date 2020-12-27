#!/usr/bin/env bash

AppName=target/smart-review-0.0.1-SNAPSHOT.jar

function startService() {
  mvn package
  nohup java -jar $AppName &
}

stopService() {
  kill "$(jps -l | grep $AppName | awk '{print $1}')"
}


if [ "$1"x == "start"x ]; then
  startService
elif [ "$1"x == "start"x ]; then
  stopService
elif [ "$1"x == "restart"x ]; then
  stopService
  startService
else
  echo "无效的参数: $1"
	echo ""
	echo "用法: ./service [参数]"
	echo "参数可以选择以下值:"
	echo "start     编译并启动项目"
	echo "stop      停止项目"
	echo "restart   重启项目"
	echo ""
fi
