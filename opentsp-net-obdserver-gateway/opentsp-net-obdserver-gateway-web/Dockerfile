FROM dockerfile/java:oracle-java8
MAINTAINER docker@navinfo.com

ADD ./opentsp-gateway-web-*[0-9]-boot.jar /service.jar

EXPOSE 8080
ENTRYPOINT java -server -Dspring.getenv.ignore=true $JAVA_OPTS -jar /service.jar
