package com.miu.waa.services.impl;

import com.miu.waa.entities.User;
import com.miu.waa.repositories.UserRepository;
import com.miu.waa.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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
}
