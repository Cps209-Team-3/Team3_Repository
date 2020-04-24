@echo off
echo Cleaning project... 
del .project .classpath gradlew* 2>NUL:
rd /s/q bin build .gradle .idea gradle 2>NUL:
echo Project is cleaned
pause
