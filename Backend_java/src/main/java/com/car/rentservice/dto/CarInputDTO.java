package com.car.rentservice.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class CarInputDTO {

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
    private final Long userId;

    @JsonCreator
    public CarInputDTO(@JsonProperty("serialNumber") String serialNumber, @JsonProperty("make") String make, @JsonProperty("modal") String modal,
                       @JsonProperty("year") String year, @JsonProperty("engine") String engine, @JsonProperty("fuel") String fuel,
                       @JsonProperty("gear") String gear, @JsonProperty("wheelsDrive") String wheelsDrive, @JsonProperty("doors") int doors,
                       @JsonProperty("seats") int seats, @JsonProperty("fuelConsumption") BigDecimal fuelConsumption, @JsonProperty("features") List<String> features,
                       @JsonProperty("carClass") String carClass, @JsonProperty("pricePerDay") BigDecimal pricePerDay, @JsonProperty("distanceIncluded") int distanceIncluded,
                       @JsonProperty("about") String about, @JsonProperty("pickUpPlace") PickUpPlaceDTO pickUpPlace, @JsonProperty("imageUrl") List<String> imageUrl,
                       @JsonProperty("userId") long userId) {
        this.serialNumber = serialNumber;
        this.make = make;
        this.modal = modal;
        this.year = year;
        this.engine = engine;
        this.fuel = fuel;
        this.gear = gear;
        this.wheelsDrive = wheelsDrive;
        this.doors = doors;
        this.seats = seats;
        this.fuelConsumption = fuelConsumption;
        this.features = features;
        this.carClass = carClass;
        this.pricePerDay = pricePerDay;
        this.distanceIncluded = distanceIncluded;
        this.about = about;
        this.pickUpPlace = pickUpPlace;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }
}
