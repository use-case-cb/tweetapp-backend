package com.tweetapp.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tweetapp.entities.User;

public interface UserRepository extends MongoRepository<User, String> {

	@Query(value = "{'username': {$regex : ?0, $options: 'i'}}")
	public List<User> findUserByUsername(String username);
	
	@Query("{username: ?0}")
	public User findUserByUsernameStrict(String username);
	
	@Query("{username: ?0,password:?1}")
	public User findUserByUsernameAndPassword(String username, String password);

}
