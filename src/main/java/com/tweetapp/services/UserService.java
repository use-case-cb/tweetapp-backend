package com.tweetapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tweetapp.entities.User;
import com.tweetapp.repos.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserService {
	
	@Autowired
	UserRepository userRepo;

	public ResponseEntity<String> createUser(User user){
		
		User duplicate = userRepo.findUserByUsernameStrict(user.getUsername());
		
		if(duplicate!=null) {
			log.info("User with name: {} already exists", user.getUsername());
			return new ResponseEntity<>("Username already in use", HttpStatus.BAD_REQUEST);
		}
		
		duplicate = userRepo.findUserByEmail(user.getEmail());
		
		if(duplicate!=null) {
			log.info("User with email: {} already exists", user.getEmail());
			return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
		}
		
		userRepo.save(user);
		log.info("User registered with Username: {}", user.getUsername());
		
		return new ResponseEntity<>("Account Created", HttpStatus.OK);
	}
	
	
	public ResponseEntity<?> loginUser(User user){
		log.info("Logging in");
		User found = userRepo.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
		
		if(found == null) {
			log.info("Login attempt failed");
			return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
		}
		
		log.info("User logged in with Username: {}", found.getUsername());
		return new ResponseEntity<>(found, HttpStatus.OK);
	}
	
	public ResponseEntity<?> forgotPassword(String username){
		log.info("Searching for forgotten password");
		User found = userRepo.findUserByUsernameStrict(username);
		
		if(found==null) {
			log.info("Username not found");
			return new ResponseEntity<>("Username not found", HttpStatus.BAD_REQUEST);
		}
		log.info("Username found");
		return new ResponseEntity<>(found.getPassword(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> searchUser(String username){
		log.info("Searching for user");
		List<User> found = userRepo.findUserByUsername(username);
		
		if(found==null) {
			log.info("User/s not found");
			return new ResponseEntity<>("User/s not found", HttpStatus.BAD_REQUEST);
		}
		log.info("User/s found");
		return new ResponseEntity<>(found, HttpStatus.OK);
	}
	
	public ResponseEntity<?> allUsers(){
		log.info("Returning all users");
		return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
	}
}
