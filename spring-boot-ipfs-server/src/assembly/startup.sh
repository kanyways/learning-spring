#!/bin/bash

SHELL_FOLDER=$(cd $(dirname $0);pwd)
FOLDER_NAME="${SHELL_FOLDER##*/}"
cd $SHELL_FOLDER

checkJava() {
  if [[ $(type -p java) != "" ]]; then
    _java=java
    startApp
  elif [[ -n $JAVA_HOME ]] && [[ -x "$JAVA_HOME/bin/java" ]]; then
    echo Found Java executable in JAVA_HOME
    _java="$JAVA_HOME/bin/java"
  else
    echo "no JRE found"
  fi
}

startApp() {
  printf "$(date)\n"
  printf "==== Starting ==== \n"
  if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo Java Version "$version"
  fi
  ## Adjust memory settings if necessary
  export JAVA_OPTS="-server -Xms1g -Xmx1g -Xss256k -Xmn600m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:-OmitStackTraceInFastThrow -Duser.timezone=Asia/Shanghai -Dclient.encoding.override=UTF-8 -Dfile.encoding=UTF-8"
  # 项目代码路径
  export CODE_HOME=`pwd`
  printf "PATH:$CODE_HOME \n"
  # 设置依赖路径
  export CLASSPATH="$CODE_HOME/classes:./lib/*"
  # 启动类
  export MAIN_CLASS=me.kany.project.learning.spring.IPFSApplication
  kill -15 `ps aux | grep -v grep| grep $MAIN_CLASS | grep $FOLDER_NAME | awk '{print $2}'`
  $_java $JAVA_OPTS -classpath $CLASSPATH $MAIN_CLASS > ./console.log 2>&1 &
  printf "================== \n"
}

checkJava