package ru.atc.notification.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class TimeUtilsImpl implements TimeUtils {
	@Override
	public Timestamp now() {
		return Timestamp.from(Instant.now());
	}
}
