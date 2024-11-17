package com.bifrost.ChatApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponseDTO {
    private String token;
    private String username;
    private Long userId;
    private String type;
    private int expiresIn;
    private String message;
}
