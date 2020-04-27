package com.car.rentservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookedPeriodDTO {

    private final String orderNumber;
    private final LocalDate startDateTime;
    private final LocalDate endDateTime;
    private final boolean paid;
    private final BigDecimal amount;
    private final LocalDate bookingDate;
}
