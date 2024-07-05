package com.miu.waa.services;

import com.miu.waa.dto.response.LoginResponse;
import com.miu.waa.entities.User;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    public LoginResponse generateToken(User user);
    public User getUserFromRequest(HttpServletRequest request);
    public String extractUsername(String token);
    public String generateVerificationToken(String email);
}
