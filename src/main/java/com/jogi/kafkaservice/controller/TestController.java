package com.jogi.kafkaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jogi.kafkaservice.configuration.Sender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

	@Autowired
	Sender sender;

	@GetMapping("/test")
	public String test() {

		log.info("Inside test method");

		sender.send("test", "Hi, welcome to kafka service");

		return "kafka message sent successfully!!!";
	}

}
