package com.bifrost.ChatApp.service;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Date;

@Service
public class TokenService {

    // Clave secreta para firmar el token (debe ser segura y almacenada de manera adecuada)
    private static final SecretKey SECRET_KEY = generateSecureKey();

    // Duración del token en milisegundos (por ejemplo, 1 hora)
    private static final long EXPIRATION_TIME = 43200000;
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    private static SecretKey generateSecureKey() {
        byte[] keyBytes = new byte[64]; // 512 bits
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);  // Llena el array con bytes aleatorios
        return new javax.crypto.spec.SecretKeySpec(keyBytes, "HmacSHA512"); // Especificamos el algoritmo "HmacSHA512"
    }

    // Genera un token JWT para el usuario dado.
    //@param username Nombre de usuario para el que se genera el token.
    //@return Token JWT como String.
    public String generateToken(String username) {
        log.info("INICIO---->generateToken :" + "username = " + username);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)  // Usamos la clave generada
                .compact();
    }


     //Valida un token JWT y verifica si es válido.
     //@param token Token JWT a validar.
     //@return Nombre de usuario (subject) si el token es válido; null en caso contrario.
     public String validateToken(String token) {
         try {
             // Usamos parserBuilder en lugar de parser
             return Jwts.parser().setSigningKey(SECRET_KEY)  // Establecemos la clave de firma
                     .build()  // Creamos el parser
                     .parseClaimsJws(token)  // Analizamos el JWT
                     .getBody()
                     .getSubject();
         } catch (Exception e) {
             return null; // Token inválido
         }
     }
}

