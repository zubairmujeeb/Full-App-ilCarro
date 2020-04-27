package com.car.rentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BestBookedCarsOutputDTO {
	private String serialNumber;
	private long bookCar;
}
