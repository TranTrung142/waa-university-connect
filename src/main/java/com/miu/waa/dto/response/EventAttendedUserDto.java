package com.miu.waa.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EventAttendedUserDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
