package com.miu.waa.services.impl;

import com.miu.waa.dto.UserDto;
import com.miu.waa.entities.User;
import com.miu.waa.entities.UserStatus;
import com.miu.waa.mapper.UserDtoMapper;
import com.miu.waa.repositories.UserRepository;
import com.miu.waa.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(user -> UserDtoMapper.dtoMapper.toUserDto(user)).toList();
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(user -> UserDtoMapper.dtoMapper.toUserDto(user)).orElse(null);
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

    public Optional<User> updateStatusUser(Long id, UserStatus status) {
        return userRepository.findById(id).map( user -> {
            user.setStatus(status);
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
