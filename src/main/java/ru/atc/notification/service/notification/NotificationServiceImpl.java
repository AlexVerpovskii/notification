package ru.atc.notification.service.notification;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

import static java.util.Objects.nonNull;

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
		final var notification = this.getNotificationEntityAll(filter);
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
	public Long getNotificationCount(NotificationFilter filter) {
		final var notification = this.getNotificationEntityAll(filter);
		return (long) notification.size();
	}

	@Override
	public void createNotification(KafkaMessage message) {
		final var notification = messageToNotificationConverter.convert(message);
		final var countMessage = new WebSocketMessage();
		if (nonNull(notification)) {
			repository.save(notification);
			countMessage.setCount(repository.countNotificationByUserAndService(notification.getServiceId(),
					notification.getUserId()));
			webSocketService.send(notification.getUserId(), WebSocketUrl.PATH, countMessage);
		} else {
			log.warn("invalid message");
		}
	}

}
