package ru.atc.notification.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class ResponseNotificationListDTO {
	public boolean success;

	public String errorMessage;

	public Integer count;

	public List<NotificationDTO> data;
}
