#!/usr/bin/env bash

function backup {
  zip -r SmartReview.zip data/
}

function update() {
  git pull
  docker-compose down
  docker pull ghcr.io/lizec123/smart-review:latest
  docker-compose up -d
}

function init {
  dataFile=data/SmartReview.db
  if [ ! -f "${dataFile}" ]; then
    echo "初始化数据库."
    sqlite3 "${dataFile}" < app/sql/init_sqlite.sql
  elif [ "$1"x == "-f"x ]; then
    echo "重置数据库."
    rm ${dataFile}
    sqlite3 "${dataFile}" < app/sql/init_sqlite.sql
  else
    echo "数据文件${dataFile}已存在, 使用 -f 参数强制更新."
  fi
}


if [ "$1"x == "backup"x ]; then
  backup
elif [ "$1"x == "update"x ]; then
  update
elif [ "$1"x == "init"x ]; then
  init $2
else
  echo "无效的参数: $1"
  echo ""
  echo "用法: ./service [参数]"
  echo "参数可以选择以下值:"
  echo "init      初始化数据库"
  echo "update    更新项目代码并重启"
fi
