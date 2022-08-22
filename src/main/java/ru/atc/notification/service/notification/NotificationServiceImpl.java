package ru.atc.notification.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.atc.notification.converter.KafkaMessageToNotificationConverter;
import ru.atc.notification.service.webSocket.WebSocketService;
import ru.atc.notification.converter.NotificationEntityToDTOConverter;
import ru.atc.notification.model.message.KafkaMessage;
import ru.atc.notification.model.message.WebSocketMessage;
import ru.atc.notification.model.dto.ResponseNotificationDTO;
import ru.atc.notification.model.entity.NotificationEntity;
import ru.atc.notification.model.repository.NotificationRepository;
import ru.atc.notification.util.singleton.WebSocketUrl;
import ru.atc.notification.util.filter.NotificationFilter;
import ru.atc.notification.util.specification.NotificationSpecification;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository repository;
	private final NotificationSpecification specification;
	private final NotificationEntityToDTOConverter converter;
	private final WebSocketService<WebSocketMessage> webSocketService;
	private final KafkaMessageToNotificationConverter messageToNotificationConverter;

	@Override
	public List<NotificationEntity> getNotificationEntityAll(NotificationFilter filter) {
		final var notifications = repository.findAll(specification.getSpecification(filter),
				filter.pageRequest());
		return notifications.stream().collect(Collectors.toList());
	}

	@Override
	public ResponseNotificationDTO getNotificationDtoAll(NotificationFilter filter) {
		final var notification = getNotificationEntityAll(filter);
		return ResponseNotificationDTO.builder()
				.data(notification
						.stream()
						.map(converter::convert)
						.collect(Collectors.toList()))
				.count(notification.size())
				.success(true)
				.build();
	}

	@Override
	public int getNotificationCount(NotificationFilter filter) {
		final var notification = getNotificationEntityAll(filter);
		return notification.size();
	}

	@Override
	public void createNotification(KafkaMessage message) {
		final var notification = messageToNotificationConverter.convert(message);
		ofNullable(notification).ifPresentOrElse(n -> {
			final var countMessage = new WebSocketMessage();
			repository.save(notification);
			countMessage.setCount(repository.countNotificationByUserAndService(notification.getUserId(),
					notification.getServiceId()));
			webSocketService.send(notification.getUserId(), WebSocketUrl.PATH, countMessage);
		}, () -> log.warn("Received invalid message: {}", message));
	}

}
