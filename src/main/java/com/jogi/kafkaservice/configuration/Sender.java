package com.jogi.kafkaservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is a kafka sender for outgoing data flows to Kafka .Reactive
 * producer that sends outgoing records to topic partitions of a Kafka cluster.
 * It contains a method that sends message on the given topic and returns status
 * as true or false if the message is sent or not respectively.
 */
@Slf4j
@Component
public class Sender {

	@Autowired
	private KafkaTemplate<Integer, Object> kafkaTemplate;
	boolean status = true;

	/**
	 * This method puts the message on the topic which is passed.
	 * 
	 * @param topic   Topic on which message is to be send
	 * @param message Message
	 * @return status
	 */
	public boolean send(String topic, Object message) {
		// the KafkaTemplate provides asynchronous send methods returning a
		// Future
		log.info("Inside send");
		System.out.println("Topic ::::::::::::::::::" + topic);
		System.out.println("Topic 1111 ::::::::::::::::::" + message);

		kafkaTemplate.send(topic, message);

		return status;
		// alternatively, to block the sending thread, to await the result,
		// invoke the future's get() method
	}

}