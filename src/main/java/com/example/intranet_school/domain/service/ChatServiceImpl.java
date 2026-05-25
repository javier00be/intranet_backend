package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Conversacion;
import com.example.intranet_school.domain.model.Mensaje;
import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.domain.ports.in.ChatUseCase;
import com.example.intranet_school.domain.ports.out.ConversacionRepositoryPort;
import com.example.intranet_school.domain.ports.out.MensajeRepositoryPort;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class ChatServiceImpl implements ChatUseCase {
    private final ConversacionRepositoryPort conversacionRepositoryPort;
    private final MensajeRepositoryPort mensajeRepositoryPort;

    @Override
    public Mensaje enviarMensaje(Long emisorId, Long receptorId, String contenido) {
        Conversacion conversacion = getOCrearConversacion(emisorId, receptorId);
        Usuario emisor = new Usuario();
        emisor.setId(emisorId);
        Mensaje mensaje = Mensaje.builder()
                .conversacionId(conversacion.getId())
                .emisor(emisor)
                .contenido(contenido)
                .build();
        return mensajeRepositoryPort.save(mensaje);
    }

    @Override
    public List<Conversacion> getConversacionesByUsuario(Long usuarioId) {
        return conversacionRepositoryPort.findByParticipanteId(usuarioId);
    }

    @Override
    public List<Mensaje> getMensajesByConversacion(Long conversacionId, Long usuarioId) {
        Conversacion conversacion = conversacionRepositoryPort.findById(conversacionId)
                .orElseThrow(() -> new RuntimeException("Conversación no encontrada: " + conversacionId));

        boolean esParticipante = conversacion.getParticipante1().getId().equals(usuarioId)
                || conversacion.getParticipante2().getId().equals(usuarioId);
        if (!esParticipante) {
            throw new SecurityException("No autorizado para acceder a esta conversación");
        }
        return mensajeRepositoryPort.findByConversacionId(conversacionId);
    }

    @Override
    public Conversacion getOCrearConversacion(Long usuarioId1, Long usuarioId2) {
        return conversacionRepositoryPort.findByParticipantes(usuarioId1, usuarioId2)
                .orElseGet(() -> {
                    Usuario p1 = new Usuario();
                    p1.setId(usuarioId1);
                    Usuario p2 = new Usuario();
                    p2.setId(usuarioId2);
                    return conversacionRepositoryPort.save(
                            Conversacion.builder().participante1(p1).participante2(p2).build()
                    );
                });
    }

    @Override
    public void marcarComoLeido(Long conversacionId, Long usuarioId) {
        mensajeRepositoryPort.marcarComoLeido(conversacionId, usuarioId);
    }
}
