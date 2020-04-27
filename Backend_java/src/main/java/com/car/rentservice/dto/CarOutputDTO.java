package com.car.rentservice.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CarOutputDTO {

    private final String serialNumber;
    private final String make;
    private final String modal;
    private final String year;
    private final String engine;
    private final String fuel;
    private final String gear;
    private final String wheelsDrive;
    private final int doors;
    private final int seats;
    private final BigDecimal fuelConsumption;
    private final List<String> features;
    private final String carClass;
    private final BigDecimal pricePerDay;
    private final int distanceIncluded;
    private final String about;
    private final PickUpPlaceDTO pickUpPlace;
    private final List<String> imageUrl;

    private final List<BookedPeriodsDTO> bookedPeriods;
}
