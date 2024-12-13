package com.bifrost.ChatApp.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    @Lob // Indica que este campo puede contener un objeto grande (TEXT en la base de datos)
    @Column(name = "content") // No es necesario especificar length aquí
    private String content;
    private LocalDateTime timestamp;
    private Long replyTo;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> deletedFor = new ArrayList<>();


}
