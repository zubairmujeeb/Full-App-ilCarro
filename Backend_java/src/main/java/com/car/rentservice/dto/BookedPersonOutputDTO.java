package com.car.rentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookedPersonOutputDTO {

    private final String email;
    private final String firstName;
    private final String secondName;
    private final String phone;

}
