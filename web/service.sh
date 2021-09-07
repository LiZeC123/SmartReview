#!/usr/bin/env bash

if [ "$1"x == "restart"x ]; then
  npm install
  npm run build
fi