#!/usr/bin/env bash
set -e
# usage: check whether java codes are well formatted
cd ../

# 执行Maven命令并检查退出状态码
mvn -T 1C -f ./pom.xml net.revelc.code.formatter:formatter-maven-plugin:2.11.0:validate || true
code=$?
if [ $code -eq 0 ]; then
  echo "Maven command executed successfully"
else
  echo "Maven command executed failed"
fi

read -p "Do you need to formatter all code? (y/n) " answer

# 判断用户输入是否为y/Y
if [[ "$answer" =~ ^[Yy]$ ]]; then
  echo "Starting formatter for all modules' code."
  mvn formatter:format
else
  echo "Skipped formatting for all modules' code."
fi