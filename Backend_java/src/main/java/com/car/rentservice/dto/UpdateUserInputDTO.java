package com.car.rentservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpdateUserInputDTO {

    private final String firstName;
    private final String secondName;
    private final String phone;
    private final String photoUrl;

    @JsonCreator
    public UpdateUserInputDTO(@JsonProperty("firstName") String firstName, @JsonProperty("secondName")String secondName,
                              @JsonProperty("phone")String phone, @JsonProperty("photoUrl")String photoUrl) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.photoUrl = photoUrl;
    }
}
