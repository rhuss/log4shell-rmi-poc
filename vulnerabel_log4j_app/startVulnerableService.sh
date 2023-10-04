#!/bin/sh
cp web/index.html.orig web/index.html
./mvnw clean package
if [ $? -eq 0 ]; then
    echo "Staring Vulnerable Service"
    java -jar target/vulnerabel_log4j_app-0.0.1-SNAPSHOT.jar
else
    echo "Failed to compile Vulnerable Service"
fi