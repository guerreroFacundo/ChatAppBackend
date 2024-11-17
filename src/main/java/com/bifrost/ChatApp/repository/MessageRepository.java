package com.bifrost.ChatApp.repository;

import com.bifrost.ChatApp.entitie.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdOrderByTimestampAsc(Long senderId, Long receiverId); // Obtener mensajes entre dos usuarios
    List<Message> findByReceiverIdAndSenderIdOrderByTimestampAsc(Long receiverId, Long senderId); // Obtener mensajes entre dos usuarios (inverso)
    // Encuentra IDs únicos de receptores en los mensajes enviados por el usuario
    @Query("SELECT DISTINCT m.receiverId FROM Message m WHERE m.senderId = :userId")
    List<Long> findDistinctReceiverIdsBySenderId(@Param("userId") Long userId);

    // Encuentra IDs únicos de remitentes en los mensajes recibidos por el usuario
    @Query("SELECT DISTINCT m.senderId FROM Message m WHERE m.receiverId = :userId")
    List<Long> findDistinctSenderIdsByReceiverId(@Param("userId") Long userId);

    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(Long senderId, Long receiverId, Long receiverId2, Long senderId2);

}

