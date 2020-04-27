package com.car.rentservice.service;

import com.car.rentservice.dto.RegisterUserInputDTO;
import com.car.rentservice.dto.UserSuccessResponseDTO;

public interface AuthenticationService {
    UserSuccessResponseDTO register(RegisterUserInputDTO registerUserInputDTO);
}
