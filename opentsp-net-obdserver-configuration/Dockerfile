FROM dockerfile/java:oracle-java8
MAINTAINER docker@navinfo.com

ADD ./*-*[0-9]-boot.jar /service.jar

EXPOSE 8900
ENTRYPOINT java -server -Dspring.getenv.ignore=true $JAVA_OPTS -jar -Dspring.profiles.active=git /service.jar