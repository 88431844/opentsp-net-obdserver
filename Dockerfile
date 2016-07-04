FROM dockerfile/java:oracle-java8
MAINTAINER docker@navinfo.com

ADD ./*-*[0-9]-boot.jar /service.jar

ENTRYPOINT java -server -Dspring.getenv.ignore=true $JAVA_OPTS -jar /service.jar