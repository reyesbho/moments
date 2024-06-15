package com.astra.moments.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class LoginResponse {
    private String token;

    private long expiresIn;
}
