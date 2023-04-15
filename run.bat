@echo off

mvn clean install -DskipTests assembly:single -q
java -jar target\naviledger.jar sample_input\input1.txt