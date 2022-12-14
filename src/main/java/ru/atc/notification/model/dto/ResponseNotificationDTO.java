package ru.atc.notification.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class ResponseNotificationDTO {

	public boolean success;

	public String errorMessage;

	public NotificationDTO data;
}
