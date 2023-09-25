#!/bin/bash

java --version

java --illegal-access=warn --add-opens java.base/jdk.internal.misc=ALL-UNNAMED -Dio.netty.tryReflectionSetAccessible=true -jar /app/fluentia.jar