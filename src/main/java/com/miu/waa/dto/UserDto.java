package com.miu.waa.dto;

import com.miu.waa.entities.UserRole;
import com.miu.waa.entities.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String profilePictureURL;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime lockTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
