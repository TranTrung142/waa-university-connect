package com.miu.waa.services;

import com.miu.waa.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getAll();
    User getUserById(Long id);
    User findUserByEmail(String email);
    Optional<User> createUser(User u);
    Optional<User> updateUser(Long id, User user);
    void deleteUser(Long id);
}
