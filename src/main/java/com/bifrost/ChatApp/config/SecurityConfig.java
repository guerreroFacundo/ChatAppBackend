package com.bifrost.ChatApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * Configuración de seguridad para la aplicación.
 * Esta clase se encarga de definir las configuraciones necesarias para el
 * manejo de la seguridad en la aplicación, como la autorización de
 * peticiones y la codificación de contraseñas.
 *
 * La clase utiliza anotaciones de Spring Security para establecer las
 * configuraciones mediante la definición de beans que serán gestionados
 * por el contenedor de Spring.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().permitAll()  // Permite acceso a todos los endpoints
                )
                .csrf(AbstractHttpConfigurer::disable)  // Desactiva CSRF en versiones 6.1 y posteriores
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
