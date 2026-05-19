package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.application.dto.AuthResponse;
import com.example.intranet_school.application.dto.LoginRequest;
import com.example.intranet_school.application.dto.RegisterRequest;

public interface AuthUseCase {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}