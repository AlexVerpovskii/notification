package ru.atc.notification.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Value("${keycloak.enabled}")
	private boolean isSecurity;

	@Override
	public String getName() {
		if (isSecurity) {
			final var authentication = SecurityContextHolder.getContext().getAuthentication();
			return authentication.getName();
		} else {
			return "anonymousUser";
		}
	}
}
