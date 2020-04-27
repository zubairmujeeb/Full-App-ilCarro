package com.car.rentservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Getter
public class RegisterUserInputDTO {

    @NotBlank  /* if you don't need these validations we can remove them*/
    @NonNull
    private final String email;
    @NotBlank
    @NonNull
    private final String firstName;
    private final String secondName;
    @NotBlank
    @NonNull
    private final String phone;
    private final String photoUrl;
    @NotBlank
    @NonNull
    private final String password;

    @JsonCreator
    public RegisterUserInputDTO(@JsonProperty("email") String email, @JsonProperty("firstName") String firstName,
                                @JsonProperty("secondName") String secondName, @JsonProperty("phone") String phone,
                                @JsonProperty("photoUrl") String photoUrl, @JsonProperty("password") String password) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.password = password;
    }
}
