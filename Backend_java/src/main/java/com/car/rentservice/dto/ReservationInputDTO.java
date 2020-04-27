package com.car.rentservice.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ReservationInputDTO {

	private final LocalDate startDateTime;
	private final LocalDate endDateTime;
	private final BookedPersonOutputDTO bookedPerson;

	@JsonCreator
	public ReservationInputDTO(@JsonProperty("startDateTime") LocalDate startDateTime,
			@JsonProperty("endDateTime") LocalDate endDateTime,
			@JsonProperty("bookedPerson") BookedPersonOutputDTO bookedPerson) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.bookedPerson = bookedPerson;
	}
}
