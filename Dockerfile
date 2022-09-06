FROM openjdk:17

COPY target/tweetapp-docker.jar tweetapp-docker.jar

ENTRYPOINT [“java”, “-jar”, “/tweetapp-docker.jar”]