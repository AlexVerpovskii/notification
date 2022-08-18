package ru.atc.notification.model.entity;

import lombok.Data;
import ru.atc.notification.util.enums.StatusEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "notification", schema = "demo")
@Data
public class NotificationEntity implements Serializable {

	@Id
	@SequenceGenerator(name = "notificationGen", sequenceName = "demo.notification_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationGen")
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "service_id")
	private String serviceId;

	@Column(name = "type_id")
	private String typeId;

	@Column(name = "sys_crt")
	private Timestamp sysCrt;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusEnum status;

	@Column(name = "description")
	private String description;

	@Column(name = "date_read")
	private Timestamp dateRead;

	@OneToMany(mappedBy = "notification", fetch = FetchType.LAZY)
	private List<ServiceNotificationEntity> serviceNotification;
}
