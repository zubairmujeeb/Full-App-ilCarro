package com.car.rentservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JwtRequest implements Serializable {
    private String email;
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }
}
