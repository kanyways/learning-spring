@echo off
setlocal enabledelayedexpansion
rem 调用VS环境
call "C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\vcvars64.bat"
set JAVA_HOME=%GRAALVM_HOME%
mvn clean -P windows package