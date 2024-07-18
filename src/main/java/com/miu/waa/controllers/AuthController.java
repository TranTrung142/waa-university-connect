package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.LoginRequest;
import com.miu.waa.dto.response.LoginResponse;
import com.miu.waa.entities.User;
import com.miu.waa.entities.UserStatus;
import com.miu.waa.services.JwtService;
import com.miu.waa.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User userLogin = userService.findUserByEmail(loginRequest.getEmail());

            if (userLogin == null || !userLogin.getStatus().equals(UserStatus.ACTIVATED)) {
                return ResponseEntity.status(401).body(new ErrorResponse(401,"Invalid account!", null));
            }

            if (userLogin.getFailedLoginAttempts() >= 5 && userLogin.getLockTime() != null && userLogin.getLockTime().isAfter(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(new ErrorResponse(401,"Account is locked!", null));
            }
            if (!passwordEncoder.matches(loginRequest.getPassword(), userLogin.getPassword())) {
                userService.updateFailedLoginAttempts(userLogin);
                return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(new ErrorResponse(401,"Invalid account!", null));
            }
            if (userLogin.getFailedLoginAttempts() >= 5 && userLogin.getLockTime() != null && userLogin.getLockTime().isBefore(LocalDateTime.now())) {
                userService.resetFailedLoginAttempts(userLogin);
            }
            return ResponseEntity.ok(new SuccessResponse(jwtService.generateToken(userLogin)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }

    }
}
