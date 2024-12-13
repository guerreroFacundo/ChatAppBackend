package com.bifrost.ChatApp.service;

import com.bifrost.ChatApp.dto.UserDTO;
import com.bifrost.ChatApp.entitie.Message;
import com.bifrost.ChatApp.exception.MensajeNoEncontradoException;
import com.bifrost.ChatApp.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * MessageService es una clase de servicio que maneja las operaciones
 * relacionadas con el envío y recepción de mensajes en la aplicación de chat.
 * Se encarga de interactuar con el MessageRepository y el UserService
 * para realizar las funciones necesarias para gestionar los mensajes.
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MessageService {


    private final MessageRepository messageRepository;
    private final UserService userService;

    // Enviar un mensaje
    public Message sendMessage(Long senderId, Long receiverId, String content,Long replyTo ) {
        log.info("INICIO---->sendMessage :senderId = {}, receiverId = {}, content = {}", senderId, receiverId, content);
        Message mensajeEnviado = new Message();
        mensajeEnviado.setSenderId(senderId);
        mensajeEnviado.setReceiverId(receiverId);
        mensajeEnviado.setContent(content);
        mensajeEnviado.setSenderName(userService.getUsername(senderId));
        log.info(mensajeEnviado.getSenderName());
        mensajeEnviado.setTimestamp(LocalDateTime.now());
        mensajeEnviado.setReplyTo(replyTo); // Establece el ID del mensaje al que se responde

        log.info(mensajeEnviado.toString());
        try {
            mensajeEnviado = messageRepository.save(mensajeEnviado);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return mensajeEnviado;
    }

    // Recuperar mensajes entre dos usuarios
    public List<Message> getMessagesBetween(Long userId, Long contactId) {
        log.info("INICIO---->getMessagesBetween :userId = {}, contactId = {}", userId, contactId);

        // Recupera todos los mensajes que no han sido marcados como "borrados" para el usuario
        return messageRepository.findByUsers(userId, contactId)
                .stream()
                .filter(message -> !message.getDeletedFor().contains(userId)) // Filtra los mensajes borrados para el usuario
                .toList();
    }

    // Recuperar todos los mensajes de un usuario (como histórico)
    public List<Message> getAllMessagesForUser(Long userId) {
        return messageRepository.findAll();
    }

    public List<UserDTO> getUserChatContacts(Long userId) {
        log.info("INICIO---->getUserChatContacts :userId = {}", userId);
        // Obtiene todos los IDs únicos con los que el usuario tiene mensajes como remitente o receptor
        List<Long> senderContacts = messageRepository.findDistinctReceiverIdsBySenderId(userId);
        List<Long> receiverContacts = messageRepository.findDistinctSenderIdsByReceiverId(userId);

        // Combina ambas listas y elimina duplicados
        Set<Long> contactIds = new HashSet<>();
        contactIds.addAll(senderContacts);
        contactIds.addAll(receiverContacts);
        // Obtener los detalles de cada usuario
        List<UserDTO> dtoUserList = userService.findUsersDTOsByIds(new ArrayList<>(contactIds));
        log.info(dtoUserList.toString());
        return dtoUserList;
    }

    public void deleteMessageForUser(Long messageId, Long userId) {
        log.info("INICIO---->deleteMessageForUser :messageId = {}, userId = {}", messageId, userId);

        // Recuperar el mensaje por su ID
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() ->  new MensajeNoEncontradoException("El mensaje con ID " + messageId + " no fue encontrado."));

        // Marcar el mensaje como eliminado si aún no está en la lista
        if (!message.getDeletedFor().contains(userId)) {
            message.getDeletedFor().add(userId);
            messageRepository.save(message);
        }
    }


}
