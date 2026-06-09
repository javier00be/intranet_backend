package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.UsuarioDTO;
import com.example.intranet_school.application.dto.chat.ConversacionDTO;
import com.example.intranet_school.application.dto.chat.MensajeDTO;
import com.example.intranet_school.domain.model.Conversacion;
import com.example.intranet_school.domain.model.Mensaje;
import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.domain.ports.in.ChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.intranet_school.infrastructure.adapter.web.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {
    private final ChatUseCase chatUseCase;

    @GetMapping("/conversaciones")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ConversacionDTO>> getMisConversaciones(
            Authentication auth) {
        Long userId = ((UserPrincipal) auth.getPrincipal()).getId();
        return ResponseEntity.ok(chatUseCase.getConversacionesByUsuario(userId).stream()
                .map(this::toConversacionDTO).collect(Collectors.toList()));
    }

    @GetMapping("/conversaciones/{conversacionId}/mensajes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MensajeDTO>> getMensajes(
            @PathVariable Long conversacionId,
            Authentication auth) {
        Long userId = ((UserPrincipal) auth.getPrincipal()).getId();
        return ResponseEntity.ok(chatUseCase.getMensajesByConversacion(conversacionId, userId).stream()
                .map(this::toMensajeDTO).collect(Collectors.toList()));
    }

    @PostMapping("/mensajes/send")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MensajeDTO> sendMensaje(
            @RequestBody Map<String, Object> body,
            Authentication auth) {
        Long emisorId = ((UserPrincipal) auth.getPrincipal()).getId();
        Long receptorId = ((Number) body.get("receptorId")).longValue();
        String contenido = (String) body.get("contenido");
        Mensaje mensaje = chatUseCase.enviarMensaje(emisorId, receptorId, contenido);
        return ResponseEntity.ok(toMensajeDTO(mensaje));
    }

    @PostMapping("/conversaciones")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ConversacionDTO> iniciarConversacion(
            @RequestBody Map<String, Long> body,
            Authentication auth) {
        Long emisorId = ((UserPrincipal) auth.getPrincipal()).getId();
        Long receptorId = body.get("receptorId");
        return ResponseEntity.ok(toConversacionDTO(
                chatUseCase.getOCrearConversacion(emisorId, receptorId)));
    }

    private ConversacionDTO toConversacionDTO(Conversacion c) {
        return ConversacionDTO.builder()
                .id(c.getId())
                .participante1(toUsuarioDTO(c.getParticipante1()))
                .participante2(toUsuarioDTO(c.getParticipante2()))
                .creadoEn(c.getCreadoEn())
                .build();
    }

    private MensajeDTO toMensajeDTO(Mensaje m) {
        String nombre = m.getEmisor() != null && m.getEmisor().getNombre() != null
                ? m.getEmisor().getNombre() + " " + m.getEmisor().getApellido() : "";
        return MensajeDTO.builder()
                .id(m.getId())
                .conversacionId(m.getConversacionId())
                .emisorId(m.getEmisor() != null ? m.getEmisor().getId() : null)
                .emisorNombre(nombre)
                .contenido(m.getContenido())
                .enviadoEn(m.getEnviadoEn())
                .leido(m.getLeido())
                .build();
    }

    private UsuarioDTO toUsuarioDTO(Usuario u) {
        if (u == null) return null;
        return UsuarioDTO.builder()
                .id(u.getId())
                .email(u.getEmail())
                .nombre(u.getNombre())
                .apellido(u.getApellido())
                .rol(u.getRol())
                .avatar(u.getAvatar())
                .build();
    }
}
