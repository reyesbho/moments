package com.astra.moments.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String user;
    private String fullName;
    private String password;
}
