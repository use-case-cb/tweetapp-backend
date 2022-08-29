package com.tweetapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.User;
import com.tweetapp.kafka.TweetEventProducer;
import com.tweetapp.repos.TweetRepository;
import com.tweetapp.repos.UserRepository;
import com.tweetapp.services.TweetService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@EnableMongoRepositories
@Slf4j
@CrossOrigin(origins = "*")
public class TweetController {
	
	@Autowired
	TweetRepository tweetRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired 
	TweetEventProducer tweetEventProducer;
	
	@Autowired
	TweetService tweetService;
	
	@RequestMapping(value = "/tweets", method = RequestMethod.GET)
	public ResponseEntity<?> showAllTweets(){
		return tweetService.allTweets();
	}
	
	@RequestMapping(value = "/{username}/tweets", method = RequestMethod.GET)
	public ResponseEntity<?> showTweets(@PathVariable("username") String username){
		return tweetService.tweets(username);
	}
	
	@RequestMapping(value = "/{username}/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addTweet(@PathVariable("username") String username, @RequestBody Tweet tweet) throws JsonProcessingException {
		return tweetService.addTweet(username, tweet);
	}
	
	@RequestMapping(value = "/tweet/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> returnTweet(@PathVariable("id") String id) {
		return tweetService.findTweet(id);
	}
	
	@RequestMapping(value = "/{username}/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editTweet(@PathVariable("username") String username, @PathVariable("id") String id, @RequestBody Tweet tweet) {
		return tweetService.updateTweet(id, tweet);
	}
	
	@RequestMapping(value = "/{username}/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTweet(@PathVariable("username") String username, @PathVariable("id") String id) {
		return tweetService.deleteTweet(id);
	}
	
	@RequestMapping(value = "/{username}/like/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> likeTweet(@PathVariable("username") String username, @PathVariable("id") String id) {
		return tweetService.likeTweet(username, id);
	}
	
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.GET)
	public List<User> getLikes(@PathVariable("id") String id){
		Tweet found = tweetRepo.findTweetById(id);
		return found.getLikes();
	}
	
	@RequestMapping(value = "/{username}/reply/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> replyTweet(@PathVariable("username") String username, @PathVariable("id") String id, @RequestBody Tweet reply) {
		return tweetService.replyTweet(username, id, reply);
	}
	

	@RequestMapping(value = "/tweet/{id}/replies", method = RequestMethod.GET)
	public List<Tweet> getReplies(@PathVariable("id") String id){
		List<Tweet> replies = tweetRepo.findTweetById(id).getReplies();
		log.info("Returning all tweets");
		return replies;
	}

	
	
}
