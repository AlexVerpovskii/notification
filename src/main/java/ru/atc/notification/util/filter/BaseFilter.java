package ru.atc.notification.util.filter;

import lombok.Data;
import org.springframework.data.domain.PageRequest;

@Data
public class BaseFilter {
	private int limit = 10;
	private int page = 0;

	public PageRequest pageRequest() {
		return PageRequest.of(page, limit);
	}
}
