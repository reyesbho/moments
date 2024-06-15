package com.astra.moments.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/public/messages")
    public ResponseEntity<String> publicMessages(){
        return ResponseEntity.ok("Public messages");
    }
}
