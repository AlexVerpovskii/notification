package ru.atc.notification.controller.notification;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "The Notification API", description = "Rest controller")
public class NotificationController {
	private final NotificationService service;

	@Tag(name = "The Notification API", description = "Get all notification")
	@GetMapping(value = "/")
	public ResponseEntity<ResponseNotificationListDTO> getAll(NotificationFilter filter) {
		try {
			return new ResponseEntity<>(service.getNotificationDtoAll(filter), OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(ResponseNotificationListDTO.builder().errorMessage(exception.getMessage()).build(),
					INTERNAL_SERVER_ERROR);
		}
	}

	@Tag(name = "The Notification API", description = "Read notification")
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
