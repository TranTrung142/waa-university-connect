package com.miu.waa.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserPermission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    STUDENT_READ("admin:read"),
    STUDENT_UPDATE("admin:update"),
    STUDENT_CREATE("admin:create"),
    STUDENT_DELETE("admin:delete");


    private final String permission;
}
