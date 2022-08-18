package ru.atc.notification.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "service_notification", schema = "demo")
@Data
public class ServiceNotificationEntity implements Serializable {

	@Id
	@SequenceGenerator(name = "serviceNotificationGen", sequenceName = "service_notification_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "serviceNotificationGen")
	@Column(unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id", nullable = false)
	private ServiceEntity service;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "notification_id", nullable = false)
	private NotificationEntity notification;
}
