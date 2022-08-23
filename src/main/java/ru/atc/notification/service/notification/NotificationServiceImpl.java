package ru.atc.notification.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.atc.notification.converter.KafkaMessageToNotificationConverter;
import ru.atc.notification.converter.NotificationEntityToDTOConverter;
import ru.atc.notification.model.dto.ResponseNotificationDTO;
import ru.atc.notification.model.message.KafkaMessage;
import ru.atc.notification.model.message.WebSocketMessage;
import ru.atc.notification.model.dto.ResponseNotificationListDTO;
import ru.atc.notification.model.entity.NotificationEntity;
import ru.atc.notification.model.repository.NotificationRepository;
import ru.atc.notification.service.user.UserService;
import ru.atc.notification.service.webSocket.WebSocketService;
import ru.atc.notification.util.TimeUtils;
import ru.atc.notification.util.enums.StatusEnum;
import ru.atc.notification.util.filter.NotificationFilter;
import ru.atc.notification.util.singleton.WebSocketUrl;
import ru.atc.notification.util.specification.NotificationSpecification;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

	private final UserService userService;
	private final TimeUtils timeUtils;
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
	public ResponseNotificationListDTO getNotificationDtoAll(NotificationFilter filter) {
		filter.setUserId(userService.getName());
		final var notification = getNotificationEntityAll(filter);
		return ResponseNotificationListDTO.builder()
				.data(notification
						.stream()
						.map(converter::convert)
						.collect(Collectors.toList()))
				.count(notification.size())
				.success(true)
				.build();
	}

	@Override
	public void createNotification(KafkaMessage message) {
		final var notification = messageToNotificationConverter.convert(message);
		ofNullable(notification).ifPresentOrElse(n -> {
			final var countMessage = new WebSocketMessage();
			repository.save(notification); // TODO must be n
			countMessage.setCount(repository.countNotificationByUserAndService(notification.getUserId(),
					notification.getServiceId()));
			webSocketService.send(notification.getUserId(), WebSocketUrl.PATH, countMessage);
		}, () -> log.warn("Received invalid message: {}", message));
	}

	@Override
	public ResponseNotificationDTO readNotification(Long id) {
		final var notification = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("notification entity with id " + id));
		if (userService.getName().equals(notification.getUserId())) {
			notification.setStatus(StatusEnum.Y);
			notification.setDateRead(timeUtils.now());
			repository.save(notification);
			return ResponseNotificationDTO.builder()
					.data(converter.convert(notification))
					.success(true)
					.build();
		} else {
			return ResponseNotificationDTO
					.builder()
					.success(false)
					.errorMessage("unavailable for this user")
					.build();
		}
	}

}
