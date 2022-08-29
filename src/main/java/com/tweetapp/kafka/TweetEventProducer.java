package com.tweetapp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.domain.TweetEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TweetEventProducer {

	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public void sendTweetEvent(TweetEvent tweetEvent) throws JsonProcessingException {
		
		//kafkaTemplate.send("tweet-events", objectMapper.writeValueAsString(tweetEvent));
		
		Integer key = tweetEvent.getTweetEventId();
		String value = objectMapper.writeValueAsString(tweetEvent);
		
		
		ListenableFuture<SendResult<Integer,String>> listenableFuture =  kafkaTemplate.send("Tweet-events",key,value);
		
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				handleSuccess(key,value,result);
			}

			@Override
			public void onFailure(Throwable ex) {
				handleFailure(key, value, ex);
			}
			
		});
	}

	protected void handleFailure(Integer key, String value, Throwable ex) {
		log.error("Error sending message, exception: {}", ex.getMessage());
		try {
			throw ex;
		} catch (Throwable throwable) {
			log.error("Error in failure: {}", throwable.getMessage());
		}
	}

	protected void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
		log.info("Message sent for the key : {} and the value {}, partition is {}", key, value, result.getRecordMetadata().partition());
	}
}
