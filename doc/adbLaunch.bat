@echo off
pause
adb tcpip 5555
set /p phoneIp="Enter Phone Ip "
adb connect %phoneIp%
adb devices
pause
