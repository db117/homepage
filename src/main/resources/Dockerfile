FROM java:8-jre-alpine
EXPOSE 8080

VOLUME /tmp
ADD homepage-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
