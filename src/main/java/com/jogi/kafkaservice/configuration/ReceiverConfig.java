package com.jogi.kafkaservice.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * This class provides all the configurations for Kafka Receiver. It configures
 * boot strap servers , provides consumer configs i.e. a map that contains list
 * of host:port pairs used for establishing the initial connections to the Kakfa
 * cluster.
 *
 */
@Configuration
@EnableKafka
public class ReceiverConfig {

	@Value("${kafka.servers.bootstrap}")
	private String bootstrapServers;

	/**
	 * This method returns a map that contains list of host:port pairs used for
	 * establishing the initial connections to the Kakfa cluster.
	 * 
	 * @return map of host port pairs
	 */
	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "50");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		return props;
	}

	/**
	 * This method returns a new DefaultKafkaConsumerFactory object.
	 * 
	 * @return ConsumerFactory object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ConsumerFactory<Integer, Object> consumerFactory() {
		return new DefaultKafkaConsumerFactory(consumerConfigs(), new StringDeserializer(),
				new JsonDeserializer(String.class));
	}

	/**
	 * This method returns a new ConcurrentKafkaListenerContainerFactory object.
	 * 
	 * @return ConcurrentKafkaListenerContainerFactory object
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, Object> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(10);
		factory.getContainerProperties().setPollTimeout(3000);
		return factory;
	}

}