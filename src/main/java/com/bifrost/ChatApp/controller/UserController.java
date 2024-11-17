package com.bifrost.ChatApp.controller;

import com.bifrost.ChatApp.dto.LoginRequestDTO;
import com.bifrost.ChatApp.dto.LoginResponseDTO;
import com.bifrost.ChatApp.entitie.User;
import com.bifrost.ChatApp.service.TokenService;
import com.bifrost.ChatApp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;
    private final TokenService tokenService;

    // Registrar usuario
    @PostMapping("/register")
    @CrossOrigin
    @Transactional
    public ResponseEntity<Map<String, String>> registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        log.info("INICIO---->registerUser :" + "username = " + username);

        User newUser = userService.registerUser(username, password, email);
        String token = tokenService.generateToken(username); // Genera un token al registrar al usuario
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", newUser.getUsername());
        log.info(response.toString());
        return ResponseEntity.ok(response);

    }

    // Autenticar usuario (aquí puedes usar autenticación básica o JWT)
    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        log.info("INICIO---->loginUser :" + "username = " + loginRequestDTO.getUsername());
        User user = userService.authenticateUser(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        // Generar token JWT para el usuario autenticado
        String token = tokenService.generateToken(loginRequestDTO.getUsername());
        LoginResponseDTO response = new LoginResponseDTO(token,loginRequestDTO.getUsername(),user.getId(), "Bearer",43200000,"LogIn exitoso");
        return ResponseEntity.ok(response);
    }
}

