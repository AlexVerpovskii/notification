package ru.atc.notification.model.dto;

import lombok.Data;

@Data
public class NotificationDTO {
	private Long id;
	private String title;
	private String user;
	private String service;
	private String type;
	private String status;
	private String description;
	private String dateRead;
}
