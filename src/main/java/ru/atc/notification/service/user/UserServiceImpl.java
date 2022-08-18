package ru.atc.notification.service.user;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
// TODO в чем смысл такого сервиса как бина? Не лучше ли сделать статичный метод с параметром Authentication
public class UserServiceImpl implements UserService {

	public String getUserName() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.equals("anonymousUser")) {
			return "test";
		}

		return authentication.getName();
	}
}
