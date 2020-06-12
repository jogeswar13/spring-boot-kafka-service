package com.jogi.kafkaservice.configuration;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Receiver {

	private static final int THREAD_POOL_SIZE = 1;
	private CountDownLatch latch0 = new CountDownLatch(THREAD_POOL_SIZE);

	@KafkaListener(topics = "${my.kafka.topic}", groupId = "savecontactgroup")
	public void receive0(String text) {
		log.info("Inside receive0");
		log.info("text: {}", text);

		latch0.countDown();
	}

}
