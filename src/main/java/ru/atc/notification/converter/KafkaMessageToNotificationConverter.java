package ru.atc.notification.converter;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.atc.notification.model.entity.NotificationEntity;
import ru.atc.notification.model.message.KafkaMessage;
import ru.atc.notification.util.TimeUtils;
import ru.atc.notification.util.enums.StatusEnum;

@AllArgsConstructor
@Component
public class KafkaMessageToNotificationConverter implements Converter<KafkaMessage, NotificationEntity> {
	private final TimeUtils timeUtils;

	@Override
	public NotificationEntity convert(KafkaMessage source) {
		final var entity = new NotificationEntity();
		entity.setTitle(source.getTitle());
		entity.setStatus(StatusEnum.N);
		entity.setDescription(source.getDescription());
		entity.setSysCrt(timeUtils.now());
		entity.setTypeId(source.getTypeId());
		entity.setUserId(source.getUserId());
		entity.setServiceId(source.getServiceId());
		return entity;
	}
}
