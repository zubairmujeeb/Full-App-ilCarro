package com.car.rentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Getter
@AllArgsConstructor
public class UserSuccessResponseDTO {

    private final String firstName;
    private final String secondName;
    private final LocalDateTime registrationDate;
    private final List<CommentsOutputDTO> comments;
    private final List<CarOutputDTO> ownCars;
    private final List<BookedCarsOutputDTO> bookedCars;
    private final List<BookedCarsOutputDTO> history;
}
