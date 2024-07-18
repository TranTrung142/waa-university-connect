package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.dto.UserDto;
import com.miu.waa.entities.User;
import com.miu.waa.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(new SuccessResponse(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDto user = userService.getUserById(id);
            if (user == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "User not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(user));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User userDto) {
        try {
            Optional<User> user = userService.createUser(userDto);
            if (user.isEmpty()) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "User not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(user.get()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDto) {
        try {
            UserDto foundUser = userService.getUserById(id);
            if (foundUser == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "User not found", null));
            }

            Optional<User> user = userService.updateUser(id, userDto);
            if (user.isEmpty()) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "User not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(user.get()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            UserDto foundUser = userService.getUserById(id);
            if (foundUser == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "User not found", null));
            }
            userService.deleteUser(id);
            return ResponseEntity.ok(new SuccessResponse("User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }
}
