package com.car.rentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PickUpPlaceDTO {

    private String placeId;
    private String placeName;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
