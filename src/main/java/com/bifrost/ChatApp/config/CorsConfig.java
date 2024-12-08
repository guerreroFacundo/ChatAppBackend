package com.bifrost.ChatApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para la aplicación.
 * Esta clase define las reglas de CORS que permiten a los navegadores web
 * realizar solicitudes de recursos a un dominio diferente al de la página
 * original.
 *
 * La configuración especificada permite orígenes específicos, métodos
 * HTTP, y encabezados requeridos para la interacción entre el servidor y
 * el cliente. Además, permite el envío de cookies si es necesario.
 *
 * Uso de {@link CorsConfigurationSource} para definir las reglas CORS y
 * {@link UrlBasedCorsConfigurationSource} para aplicarlas a todas las rutas.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Especifica explícitamente los orígenes permitidos
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:3000/chat-layout"));  // Asegúrate de agregar la URL de tu frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Permite el envío de cookies si es necesario
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
