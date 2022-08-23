package ru.atc.notification.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.atc.notification.model.dto.NotificationDTO;
import ru.atc.notification.model.entity.NotificationEntity;

import java.text.SimpleDateFormat;

import static java.util.Objects.nonNull;

@Component
public class NotificationEntityToDTOConverter implements Converter<NotificationEntity, NotificationDTO> {

	@Override
	public NotificationDTO convert(NotificationEntity source) {
		final var df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final var dto = new NotificationDTO();
		dto.setId(source.getId());
		dto.setTitle(source.getTitle());
		dto.setDescription(source.getDescription());
		dto.setUser(source.getUserId());
		dto.setStatus(source.getStatus().name());
		dto.setService(source.getServiceId());
		dto.setType(source.getTypeId());
		if (nonNull(source.getDateRead())) {
			dto.setDateRead(df.format(source.getDateRead()));
		}
		return dto;
	}
}



