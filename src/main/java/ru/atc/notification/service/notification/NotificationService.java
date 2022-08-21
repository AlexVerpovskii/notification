package ru.atc.notification.service.notification;

import ru.atc.notification.model.message.KafkaMessage;
import ru.atc.notification.model.dto.ResponseNotificationDTO;
import ru.atc.notification.model.entity.NotificationEntity;
import ru.atc.notification.util.filter.NotificationFilter;

import java.util.List;

public interface NotificationService {
	List<NotificationEntity> getNotificationEntityAll(NotificationFilter filter);

	ResponseNotificationDTO getNotificationDtoAll(NotificationFilter filter);

	int getNotificationCount(NotificationFilter filter);

	void createNotification(KafkaMessage message);
}
