package ru.atc.notification.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.atc.notification.model.entity.NotificationEntity;
import ru.atc.notification.model.repository.base.BaseRepository;

@Repository
public interface NotificationRepository extends BaseRepository<NotificationEntity, Long> {
	@Query(value = "SELECT count(*) from demo.notification where user_id = :userId and service_id = :serviceId", nativeQuery = true)
	int countNotificationByUserAndService(String userId, String serviceId);
}
