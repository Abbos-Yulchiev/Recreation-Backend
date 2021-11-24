package com.epam.recreation_module.service;

import com.epam.recreation_module.model.DTO.LoginDTO;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> login(LoginDTO loginDTO);

    ResponseEntity<?> authToken(User user);
}
