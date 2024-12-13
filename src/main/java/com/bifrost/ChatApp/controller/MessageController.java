package com.bifrost.ChatApp.controller;

import com.bifrost.ChatApp.dto.UserDTO;
import com.bifrost.ChatApp.entitie.Message;
import com.bifrost.ChatApp.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar mensajes. Este controlador proporciona endpoints
 * para enviar, obtener y eliminar mensajes, además de consultar los chats de un usuario.
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;

    private final SimpMessagingTemplate messagingTemplate;

    // Enviar mensaje
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam String content,
            @RequestParam(required = false) Long replyTo // Nuevo parámetro opcional
    ) {
        log.info("INICIO---->sendMessage :senderId = {}, receiverId = {}, content = {}", senderId, receiverId, content);
        try {
            log.info("Default destination socket sendMessage: {}",messagingTemplate.getDefaultDestination());
            // Guarda el mensaje en la base de datos
            Message message = messageService.sendMessage(senderId, receiverId, content, replyTo); // Asegúrate de que el servicio también acepte el nuevo parámetro

            // Envía el mensaje en tiempo real al receptor a través del WebSocket
            messagingTemplate.convertAndSendToUser(
                    receiverId.toString(),   // ID del usuario receptor como String
                    "/queue/messages",       // Suscripción donde escuchará el receptor
                    message                  // Mensaje enviado
            );

//            // Opcional: también puedes enviar el mensaje al remitente para actualizar su vista
//            messagingTemplate.convertAndSendToUser(
//                    senderId.toString(),
//                    "/queue/messages",
//                    message
//            );
            log.info("Mensaje enviado a {}: {} MensajeObject: {}", receiverId, content,message);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Obtener mensajes entre dos usuarios
    @GetMapping("/obtenerMensajes")
    public List<Message> getMessages(@RequestParam Long userId, @RequestParam Long contactId) {
        log.info("INICIO---->getMessages :userId = {}, contactId = {}", userId, contactId);
        return messageService.getMessagesBetween(userId, contactId);
    }

    @GetMapping("/chats")
    public List<UserDTO> getUserChats(@RequestParam Long userId) {
        log.info("INICIO---->getUserChats :userId = {}", userId);
        // Obtener los IDs de usuarios con los que se ha intercambiado mensajes
        List<UserDTO> dtoUserList = messageService.getUserChatContacts(userId);
        return dtoUserList;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMessageForUser(
            @RequestParam Long messageId,
            @RequestParam Long userId
    ) {
        log.info("INICIO---->deleteMessageForUser :messageId = {}, userId = {}", messageId, userId);
        try {
            // Lógica para borrar el mensaje para ese usuario
            messageService.deleteMessageForUser(messageId, userId);
            return ResponseEntity.ok("El mensaje ha sido eliminado para el usuario.");
        } catch (Exception e) {
            log.error("Error al eliminar el mensaje: {}", e.getMessage());
            return ResponseEntity.badRequest().body("No se pudo eliminar el mensaje.");
        }
    }



}
