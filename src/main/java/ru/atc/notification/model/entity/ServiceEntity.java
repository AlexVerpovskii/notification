package ru.atc.notification.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "service", schema = "demo")
@Data
public class ServiceEntity implements Serializable {

	@Id
	@SequenceGenerator(name = "serviceGen", sequenceName = "service_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "serviceGen")
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
	private List<ServiceNotificationEntity> serviceNotification;
}
