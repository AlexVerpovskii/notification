package ru.atc.notification.controller.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.atc.notification.model.dto.ResponseNotificationDTO;
import ru.atc.notification.model.dto.ResponseNotificationListDTO;
import ru.atc.notification.service.notification.NotificationService;
import ru.atc.notification.util.filter.NotificationFilter;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("notification")
public class NotificationController {
	private final NotificationService service;

	@GetMapping(value = "/")
	public ResponseEntity<ResponseNotificationListDTO> getAll(NotificationFilter filter) {
		final var authentication = SecurityContextHolder.getContext().getAuthentication();
		filter.setUserId(authentication.getName());
		try {
			return new ResponseEntity<>(service.getNotificationDtoAll(filter), OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(ResponseNotificationListDTO.builder().errorMessage(exception.getMessage()).build(),
					INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ResponseNotificationDTO> read(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(service.readNotification(id), OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(ResponseNotificationDTO
					.builder()
					.errorMessage(exception.getMessage())
					.build(),
					INTERNAL_SERVER_ERROR);
		}
	}
}
