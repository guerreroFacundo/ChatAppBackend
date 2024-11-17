package com.bifrost.ChatApp.controller;

import com.bifrost.ChatApp.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j

public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/generateToken")
    @CrossOrigin
    public ResponseEntity<String> generateToken(@RequestParam String username) {
        log.info("INICIO---->generateToken :"+"username = " + username);
        try {
            String token = tokenService.generateToken(username); // Genera un token al registrar al usuario
            return ResponseEntity.ok()
                    .body(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
