package com.tweetapp.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tweetapp.repos.UserRepository;
import com.tweetapp.services.UserService;

@SpringBootTest
public class UserControllerTests {
	
	@Autowired
	UserService userService;
	
	@MockBean
	UserRepository userRepo;
	
	@Autowired 
	UserController userController;
	

	@Test
	public void controllerLoaded() throws Exception{
		assertThat(userController).isNotNull();
	}
	

}
