FROM openjdk:17
EXPOSE 8080
ADD target/tweetapp-docker.jar tweetapp-docker.jar
ENTRYPOINT ["java","-jar,","tweetapp-docker.jar"]