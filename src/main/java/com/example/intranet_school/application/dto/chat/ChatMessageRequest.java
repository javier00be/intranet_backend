package com.example.intranet_school.application.dto.chat;

import lombok.Data;

@Data
public class ChatMessageRequest {
    private Long receptorId;
    private String contenido;
}
