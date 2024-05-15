package com.astra.moments.controller;

import com.astra.moments.dto.TokenDto;
import com.astra.moments.dto.UrlDto;
import com.astra.moments.dto.UserDto;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class AuthController {

    @Value("${spring.security.oauth2.resourceserver.opaque-token.instrospection-uri.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.resourceserver.opaque-token.instrospection-uri.client-secret}")
    private String clientSecret;

    @GetMapping("/auth/url")
    public ResponseEntity<UrlDto> auth(){
        String url = new GoogleAuthorizationCodeRequestUrl(
                clientId, "http://localhost:5173", Arrays.asList("email","profile","openid")
        ).build();
        return ResponseEntity.ok(new UrlDto(url));
    }

    @GetMapping("/auth/callback")
    public ResponseEntity<TokenDto> callback(@RequestParam("code") String code){
        String token;
        try {
             token = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    "http://localhost:5173"
            ).execute().getAccessToken();

        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new TokenDto(token));
    }

    @GetMapping("/auth/user")
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal OAuth2User user){

        System.out.println(user.getAttributes());
        return ResponseEntity.ok(UserDto.builder().email(user.getAttribute("email"))
                .user(user.getAttribute("name")).build());
    }
}
