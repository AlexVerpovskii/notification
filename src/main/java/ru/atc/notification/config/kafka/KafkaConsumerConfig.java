package ru.atc.notification.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.atc.notification.model.message.KafkaMessage;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServer;

	@Value("${spring.kafka.consumer.group-id}")
	private String kafkaGroupId;

	public Map<String, Object> consumerConfigs() {
		return Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer,
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
				ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId,
				JsonDeserializer.TRUSTED_PACKAGES, "*"
		);
	}

	public ConsumerFactory<String, KafkaMessage> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> kafkaListenerContainerFactory() {
		final ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());

		ContainerProperties containerProperties = factory.getContainerProperties();
		containerProperties.setMissingTopicsFatal(false);

		return factory;
	}
}