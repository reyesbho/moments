package com.astra.moments.controller;

import com.astra.moments.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/private")
public class PrivateController {

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal user ){
            UserDto userDto = UserDto.builder()
                    .user(user.getName()).email(user.getAttribute("email")).build();
            return ResponseEntity.ok(userDto);

    }
}
