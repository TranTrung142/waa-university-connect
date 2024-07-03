package com.miu.waa.services.impl;

import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.entities.Event;
import com.miu.waa.entities.EventAttendance;
import com.miu.waa.entities.EventStatus;
import com.miu.waa.entities.User;
import com.miu.waa.mapper.EventDtoMapper;
import com.miu.waa.repositories.EventAttendanceRepository;
import com.miu.waa.repositories.EventRepository;
import com.miu.waa.repositories.UserRepository;
import com.miu.waa.services.UserService;
import com.miu.waa.utils.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> createUser(User u) {
        String encodedPassword = passwordEncoder.encode(u.getPassword());
        u.setPassword(encodedPassword);
        return Optional.ofNullable(userRepository.save(u));
    }

    public Optional<User> updateUser(Long id, User user) {
        return userRepository.findById(id).map( foundUser -> {
            user.setId(id);
            return userRepository.save(user);
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public void updateFailedLoginAttempts(User user) {
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
        if (user.getFailedLoginAttempts() >= 5) {
            user.setLockTime(LocalDateTime.now().plusSeconds(30));
        }
        userRepository.save(user);
    }

    public void resetFailedLoginAttempts(User user) {
        user.setFailedLoginAttempts(0);
        user.setLockTime(null);
        userRepository.save(user);
    }
}
