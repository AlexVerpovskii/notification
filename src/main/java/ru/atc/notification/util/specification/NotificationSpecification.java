package ru.atc.notification.util.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.atc.notification.model.entity.NotificationEntity;
import ru.atc.notification.util.filter.NotificationFilter;

@Component
public class NotificationSpecification extends BaseFilterSpecification<NotificationFilter, NotificationEntity> {

	@Override
	public Specification<NotificationEntity> getSpecification(NotificationFilter filter) {
		Specification<NotificationEntity> spec = Specification.where(null);
		if (StringUtils.isNotBlank(filter.getService())) {
			spec = spec.and(has("serviceId", filter.getService()));
		}
		if (StringUtils.isNotBlank(filter.getUserId())) {
			spec = spec.and(has("userId", filter.getUserId()));
		}
		return spec;
	}
}
