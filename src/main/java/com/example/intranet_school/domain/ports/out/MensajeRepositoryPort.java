package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Mensaje;
import java.time.LocalDateTime;
import java.util.List;

public interface MensajeRepositoryPort {
    Mensaje save(Mensaje mensaje);
    List<Mensaje> findByConversacionId(Long conversacionId);
    void deleteByEnviadoEnBefore(LocalDateTime fecha);
    void marcarComoLeido(Long conversacionId, Long usuarioId);
}
