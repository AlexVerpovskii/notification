package ru.atc.notification.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.atc.notification.model.dto.NotificationDTO;
import ru.atc.notification.model.entity.NotificationEntity;

@Component
public class NotificationEntityToDTOConverter implements Converter<NotificationEntity, NotificationDTO> {

	@Override
	public NotificationDTO convert(NotificationEntity source) {
		final var dto = new NotificationDTO();
		dto.setTitle(source.getTitle());
		dto.setDescription(source.getDescription());
		dto.setUser(source.getUserId());
		dto.setStatus(source.getStatus().name());
		dto.setService(source.getServiceId());
		dto.setType(source.getTypeId());
		return dto;
	}
}



