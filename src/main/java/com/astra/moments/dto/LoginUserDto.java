package com.astra.moments.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class LoginUserDto {
    private String email;

    private String password;

    // getters and setters here...
}
