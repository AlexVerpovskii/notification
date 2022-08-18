package ru.atc.notification.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.atc.notification.model.entity.NotificationEntity;
import ru.atc.notification.model.repository.base.BaseRepository;

@Repository
public interface NotificationRepository extends BaseRepository<NotificationEntity, Long> {
	@Query(value = "SELECT count(*) from demo.notification where service_id = :serviceId and user_id = :userId", nativeQuery = true)
	int countNotificationByUserAndService(String serviceId, String userId); // TODO В названии сначала user потом service, а параметры наоборот
}
