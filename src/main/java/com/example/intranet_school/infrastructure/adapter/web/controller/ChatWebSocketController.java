package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.chat.ChatMessageRequest;
import com.example.intranet_school.application.dto.chat.ChatMessageResponse;
import com.example.intranet_school.domain.model.Mensaje;
import com.example.intranet_school.domain.ports.in.ChatUseCase;
import com.example.intranet_school.infrastructure.adapter.web.util.MessageSanitizer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final ChatUseCase chatUseCase;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageSanitizer sanitizer;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessageRequest request, Principal principal) {
        Long emisorId = extractUserId(principal);

        String contenido = sanitizer.sanitize(request.getContenido());
        if (!sanitizer.isValid(contenido)) return;

        Mensaje mensaje = chatUseCase.enviarMensaje(emisorId, request.getReceptorId(), contenido);
        ChatMessageResponse response = toResponse(mensaje);

        // Entrega al receptor
        messagingTemplate.convertAndSendToUser(
                request.getReceptorId().toString(), "/queue/messages", response);
        // Confirmación al emisor
        messagingTemplate.convertAndSendToUser(
                emisorId.toString(), "/queue/messages", response);
    }

    @MessageMapping("/chat.read")
    public void markAsRead(@Payload Map<String, Long> payload, Principal principal) {
        Long conversacionId = payload.get("conversacionId");
        Long usuarioId = extractUserId(principal);
        if (conversacionId != null) {
            chatUseCase.marcarComoLeido(conversacionId, usuarioId);
        }
    }

    private Long extractUserId(Principal principal) {
        return (Long) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }

    private ChatMessageResponse toResponse(Mensaje mensaje) {
        String emisorNombre = mensaje.getEmisor() != null && mensaje.getEmisor().getNombre() != null
                ? mensaje.getEmisor().getNombre() + " " + mensaje.getEmisor().getApellido()
                : "";
        return ChatMessageResponse.builder()
                .id(mensaje.getId())
                .conversacionId(mensaje.getConversacionId())
                .emisorId(mensaje.getEmisor() != null ? mensaje.getEmisor().getId() : null)
                .emisorNombre(emisorNombre)
                .contenido(mensaje.getContenido())
                .enviadoEn(mensaje.getEnviadoEn())
                .leido(mensaje.getLeido())
                .build();
    }
}
