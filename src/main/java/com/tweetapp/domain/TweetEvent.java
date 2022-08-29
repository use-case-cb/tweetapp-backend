package com.tweetapp.domain;

import com.tweetapp.entities.Tweet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TweetEvent {

	private Integer tweetEventId;

	private Tweet tweet;
	
}
