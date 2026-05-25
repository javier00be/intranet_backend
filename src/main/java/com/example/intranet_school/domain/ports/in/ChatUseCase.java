package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Conversacion;
import com.example.intranet_school.domain.model.Mensaje;
import java.util.List;

public interface ChatUseCase {
    Mensaje enviarMensaje(Long emisorId, Long receptorId, String contenido);
    List<Conversacion> getConversacionesByUsuario(Long usuarioId);
    List<Mensaje> getMensajesByConversacion(Long conversacionId, Long usuarioId);
    Conversacion getOCrearConversacion(Long usuarioId1, Long usuarioId2);
    void marcarComoLeido(Long conversacionId, Long usuarioId);
}
