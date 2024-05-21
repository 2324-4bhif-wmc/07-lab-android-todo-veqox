#!/bin/bash

./gradlew assemble

if [[ "$1" == "-r" ]]
then
    adb install app/build/outputs/apk/release/app-release-unsigned.apk
else
    adb install app/build/outputs/apk/debug/app-debug.apk
fi
