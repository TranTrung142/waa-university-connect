package com.miu.waa.utils;

import com.miu.waa.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class RequestUtil {
    public static Optional<User> getUserLogin(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication)
                .map(auth -> auth.getPrincipal() instanceof User ? (User) auth.getPrincipal() : null);
    }
}
