package ru.atc.notification.model.message;

import lombok.Data;

@Data
public class KafkaMessage {
	private String serviceId;
	private String title;
	private String description;
	private String typeId;
	private String userId;
}
