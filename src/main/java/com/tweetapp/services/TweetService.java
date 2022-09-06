package com.tweetapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tweetapp.domain.TweetEvent;
import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.User;
import com.tweetapp.kafka.TweetEventProducer;
import com.tweetapp.repos.TweetRepository;
import com.tweetapp.repos.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TweetService {
	
	TweetRepository tweetRepo;
	
	public TweetService(TweetRepository tweetRepo) {
		this.tweetRepo = tweetRepo;
	}
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TweetEventProducer tweetProd;

	public ResponseEntity<?> allTweets(){
		log.info("Returning all tweets in database");
		return new ResponseEntity<>(tweetRepo.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> tweets(String username){
		log.info("Returning all tweets for user");
		User found = userRepo.findUserByUsernameStrict(username);
		if(found==null) {
			log.info("User not found");
			return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
		}
		List<Tweet> tweets = tweetRepo.findTweetsByUser(username);
		return new ResponseEntity<>(tweets, HttpStatus.OK);
	}
	
	public ResponseEntity<?> findTweet(String id){
		log.info("Finding tweet with id: {}", id);
		Tweet found = tweetRepo.findTweetById(id);
		
		if(found==null) {
			log.info("Tweet not found");
			return new ResponseEntity<>("Tweet not found", HttpStatus.OK);
		}

		List<Tweet> tweet = new ArrayList<>();
		tweet.add(found);
		return new ResponseEntity<>(tweet, HttpStatus.OK);
	}
	
	public ResponseEntity<?> addTweet(String username, Tweet tweet){
		log.info("Adding tweet");
		tweet.setAuthor(username);
		tweetRepo.save(tweet);
		return new ResponseEntity<>("Added tweet", HttpStatus.OK);
	}
	
	public ResponseEntity<?> updateTweet(String id, Tweet tweet){
		Tweet found = tweetRepo.findTweetById(id);
		System.out.println("Here: " + tweet.getContent());
		
		if(found==null) {
			log.info("Tweet not found");
			return new ResponseEntity<>("Could not find tweet", HttpStatus.BAD_REQUEST);
		}
		
		found.setContent(tweet.getContent());
		tweetRepo.save(found);
		log.info("Updating tweet with id: {}", id);
		
		return new ResponseEntity<>("Updated tweet", HttpStatus.OK);
	}
	
	public ResponseEntity<?> deleteTweet(String id){
		log.info("Deleting tweet with id {}", id);
		
		Tweet found = tweetRepo.findTweetById(id);
		
		if(found==null) {
			log.info("Tweet not found");
			return new ResponseEntity<>("Could not find tweet", HttpStatus.BAD_REQUEST);
		}
		
		tweetRepo.delete(found);
		log.info("Tweet deleted");
		
		//Commenting out kafka while testing
		/*
		try {
			tweetProd.sendTweetEvent(new TweetEvent(1,found));
			log.info("Sent Kafka message (delete tweet)");
		} catch (JsonProcessingException e) {
			log.error("Error sending Kafka message (delete tweet)");
		}
		*/
		return new ResponseEntity<>("Tweet deleted", HttpStatus.OK);
	}
	
	public ResponseEntity<?> likeTweet(String username, String id){
		log.info("Liking tweet with id {}", id);
		
		Tweet found = tweetRepo.findTweetById(id);
		
		if(found==null) {
			log.info("Tweet not found");
			return new ResponseEntity<>("Could not find tweet", HttpStatus.BAD_REQUEST);
		}

		User user = userRepo.findUserByUsernameStrict(username);
		found.addLike(user);
		tweetRepo.save(found);
		log.info("Tweet liked");
		return new ResponseEntity<>("Liked tweet", HttpStatus.OK);
	}
	
	public ResponseEntity<?> replyTweet(String username, String id, Tweet reply){
		log.info("Replying to tweet with id: {}", id);
		
		Tweet found = tweetRepo.findTweetById(id);
		
		if(found==null) {
			log.info("Tweet not found");
			return new ResponseEntity<>("Could not find tweet", HttpStatus.BAD_REQUEST);
		}
		
		reply.setAuthor(username);
		found.addReply(reply);
		tweetRepo.save(found);
		log.info("User: {} is replying to tweet with id: {}", username, id);
		return new ResponseEntity<>("Added reply", HttpStatus.OK);
	}
}
