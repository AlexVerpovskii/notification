package ru.atc.notification.config.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import ru.atc.notification.model.message.KafkaMessage;
import ru.atc.notification.service.notification.NotificationService;
import ru.atc.notification.util.singleton.KafkaTopic;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NotificationKafkaListener {
	private final NotificationService notificationService;

	@KafkaListener(topics = KafkaTopic.NOTIFICATION)
	public void notificationServiceListener(KafkaMessage message) {
		notificationService.createNotification(message);
	}
}
