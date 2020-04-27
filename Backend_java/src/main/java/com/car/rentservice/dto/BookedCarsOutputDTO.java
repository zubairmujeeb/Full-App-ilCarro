package com.car.rentservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookedCarsOutputDTO {

    private final String serialNumber;
    private final List<BookedPeriodDTO> bookedPeriodDTO;
}
