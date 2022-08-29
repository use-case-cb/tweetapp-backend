package com.tweetapp.controllers;

import javax.validation.Valid;

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

import com.tweetapp.entities.User;
import com.tweetapp.repos.UserRepository;
import com.tweetapp.services.UserService;

@RestController
@Validated
@EnableMongoRepositories
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserService	userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
			return userService.createUser(user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loginUser(@RequestBody User user) {
		return userService.loginUser(user);
	}
	
	@RequestMapping(value = "/{username}/forgot", method = RequestMethod.GET)
	public ResponseEntity<?> forgotPassword(@PathVariable("username") String username) {
		return userService.forgotPassword(username);
	}
	
	@RequestMapping(value = "/user/search/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> searchUser(@PathVariable("username") String username) {
		return userService.searchUser(username);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<?> searchAllUsers() {
		return userService.allUsers();
	}
}
