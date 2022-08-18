package ru.atc.notification.service.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService<T> {
	private final SimpMessagingTemplate messagingTemplate;

	public void send(String user, String path, T message) {
		messagingTemplate.convertAndSendToUser(user, path, message);
	}
}
