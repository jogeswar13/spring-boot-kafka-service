package com.jogi.kafkaservice.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class provides all the configurations for Kafka Receiver. It configures
 * boot strap servers , provides producerConfigs i.e. a map that contains list
 * of host:port pairs used for establishing the initial connections to the Kakfa
 * cluster.
 *
 */
@Configuration
public class SenderConfig {

	@Value("${kafka.servers.bootstrap}")
	private String bootstrapServers;

	/**
	 * This method returns a map that contains list of host:port pairs used for
	 * establishing the initial connections to the Kakfa cluster.
	 * 
	 * @return map of host port pairs
	 */
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		// list of host:port pairs used for establishing the initial connections
		// to the Kakfa cluster
		props.put(ProducerConfig.RETRIES_CONFIG, 3);
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put("request.required.acks", "0");
		// value to block, after which it will throw a TimeoutException
		props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

		return props;
	}

	/**
	 * This method returns a new DefaultKafkaProducerFactory object.
	 * 
	 * @return ProducerFactory object
	 */
	@Bean
	public ProducerFactory<Integer, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<Integer, Object>(producerConfigs());
	}

	/**
	 * This method returns a new KafkaTemplate object.
	 * 
	 * @return KafkaTemplate object
	 */
	@Bean
	public KafkaTemplate<Integer, Object> kafkaTemplate() {
		return new KafkaTemplate<Integer, Object>(producerFactory());
	}

	@Bean
	public Gson gson() {
		return new GsonBuilder().disableHtmlEscaping().setLenient().create();
	}

}