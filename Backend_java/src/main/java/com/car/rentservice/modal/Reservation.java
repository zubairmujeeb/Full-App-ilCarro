package com.car.rentservice.modal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.car.rentservice.audited.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "reservation")
@Audited
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Reservation extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservationSequence")
	@SequenceGenerator(name = "reservationSequence", sequenceName = "RESERVATION_SEQ", allocationSize = 1)
	private Long id;

	private String orderNumber;
	private BigDecimal amount;
	private LocalDate bookingDate;
	private String confirmationCode;
	private LocalDate startDateTime;
	private LocalDate endDateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	private String serialNumber;

	@Builder.Default
	private boolean paid = false;

}
