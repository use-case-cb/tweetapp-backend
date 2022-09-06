package com.tweetapp.controllers;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tweetapp.repos.TweetRepository;
import com.tweetapp.services.TweetService;

@ExtendWith(MockitoExtension.class)
public class TweetControllerTests {

	
	TweetRepository tweetRepo;
	
	TweetService tweetService;
	
	//@BeforeEach
	void setUp() {
		this.tweetService = new TweetService(tweetRepo);
		MockitoAnnotations.openMocks(this);
	}
	
	
	//@Test
	void showAllTweetsTest() throws Exception{
		tweetService.allTweets();
		verify(tweetRepo).findAll();
	}
}
