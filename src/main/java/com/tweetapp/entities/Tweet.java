package com.tweetapp.entities;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Document("tweets")
@Getter
@Setter
@Data
public class Tweet {
	
	@Id
	private String id;
	
	private String author;
	
	@NotNull
	@Size(min = 1, message = "{validation.tweet.too_short")
	@Size(max = 144, message = "{validation.tweet.too_long")
	private String content;
	
	private List<User> likes = new ArrayList<User>();
	private List<Tweet> replies = new ArrayList<Tweet>();
	
	private String tags;
	
	private String date;
	
	Tweet() {}
	
	
	public void addLike(User user) {
		likes.add(user);
	}
	
	
	public void addReply(Tweet reply) {
		replies.add(reply);
	}
	
	
}
