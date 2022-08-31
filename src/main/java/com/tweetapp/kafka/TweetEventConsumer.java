package com.tweetapp.kafka;

import org.springframework.stereotype.Component;

@Component
public class TweetEventConsumer {
	
	/*
	@KafkaListener(topics="tweet-events", groupId="group1")
	public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {
		log.info("OnMessage Record: {} ", consumerRecord);
	}
	*/
	
}
