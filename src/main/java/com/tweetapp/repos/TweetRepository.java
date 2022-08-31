package com.tweetapp.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tweetapp.entities.Tweet;

public interface TweetRepository extends MongoRepository<Tweet, String>{
	
	@Query("{id: ?0}")
	public Tweet findTweetById(String id);
	
	@Query("{'author' :?0}")
	public List<Tweet> findTweetsByUser(String author);
	
	@Query("{id ?0}")
	public int findNumLikes(String id);
}
