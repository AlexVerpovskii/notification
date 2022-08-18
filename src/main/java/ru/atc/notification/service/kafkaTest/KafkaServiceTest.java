package ru.atc.notification.service.kafkaTest;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaServiceTest<M> {

	private final KafkaTemplate<String, M> kafkaTemplate;

	public void sendMessage(String topic, M message) {
		kafkaTemplate.send(topic, UUID.randomUUID().toString(), message);
	}
}
