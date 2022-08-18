package ru.atc.notification.controller.kafkaTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.atc.notification.service.kafkaTest.KafkaServiceTest;
import ru.atc.notification.model.message.KafkaMessage;
import ru.atc.notification.util.singleton.KafkaTopic;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("kafka")
public class KafkaTestController {
	private final KafkaServiceTest<KafkaMessage> service;

	@GetMapping(value = "/")
	public void test() {
		final var message = new KafkaMessage();
		message.setTitle("test title");
		message.setDescription("test description");
		message.setServiceId("test service");
		message.setTypeId("create proposal");
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		message.setUserId(authentication.getName());
		service.sendMessage(KafkaTopic.NOTIFICATION, message);
		log.debug("send message");
	}

	@GetMapping("/me")
	public Object getMe() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
