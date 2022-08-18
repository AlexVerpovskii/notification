package ru.atc.notification.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.atc.notification.model.message.KafkaMessage;
import ru.atc.notification.util.singleton.KafkaTopic;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfigTest {

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServer;

	@Bean
	public Map<String, Object> producerConfigs() {

		Map<String, Object> props = new HashMap<>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return props;
	}

	@Bean("producerFactory")
	public ProducerFactory<String, KafkaMessage> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean("kafkaTemplate")
	public KafkaTemplate<String, KafkaMessage> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public NewTopic getNotification() {
		return new NewTopic(KafkaTopic.NOTIFICATION, 1, (short) 1);
	}
}
