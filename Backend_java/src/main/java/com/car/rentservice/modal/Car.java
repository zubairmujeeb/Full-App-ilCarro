package com.car.rentservice.modal;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.car.rentservice.audited.Auditable;
import com.car.rentservice.common.converters.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "car")
@Audited
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Car extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carSequence")
	@SequenceGenerator(name = "carSequence", sequenceName = "CAR_SEQ", allocationSize = 1)
	private Long id;
	@Column(unique = true)
	private String serialNumber;
	private String make;
	private String modal;
	private String year;
	private String engine;
	private String fuel;
	private String gear;
	private String wheelsDrive;
	private int doors;
	private int seats;
	private BigDecimal fuelConsumption;

	@Convert(converter = StringListConverter.class)
	@Column(columnDefinition = "TEXT")
	private List<String> features;

	private String carClass;
	private BigDecimal pricePerDay;
	private int distanceIncluded;

	@Column(columnDefinition = "TEXT")
	private String about;

	@OneToOne
	private PickUpPlace pickUpPlace;

	@Convert(converter = StringListConverter.class)
	@Column(columnDefinition = "TEXT")
	private List<String> imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
}
