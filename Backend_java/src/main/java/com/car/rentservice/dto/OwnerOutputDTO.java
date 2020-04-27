package com.car.rentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OwnerOutputDTO {

    private final String firstName;
    private final String secondName;
    private final LocalDateTime registrationDate;
}
