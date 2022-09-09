FROM openjdk:17

COPY target/tweetapp-docker.jar tweetapp-docker.jar

CMD java -jar /tweetapp-docker.jar