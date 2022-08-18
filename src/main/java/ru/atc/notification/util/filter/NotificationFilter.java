package ru.atc.notification.util.filter;

import lombok.Data;

@Data
public class NotificationFilter extends BaseFilter {
	private String service;
	private String userId;
}
