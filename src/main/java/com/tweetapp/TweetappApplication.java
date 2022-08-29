package com.tweetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TweetappApplication{
	
	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "local");
		SpringApplication.run(TweetappApplication.class, args);
	}
}
