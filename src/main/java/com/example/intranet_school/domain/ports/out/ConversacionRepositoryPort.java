package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Conversacion;
import java.util.List;
import java.util.Optional;

public interface ConversacionRepositoryPort {
    Conversacion save(Conversacion conversacion);
    Optional<Conversacion> findById(Long id);
    List<Conversacion> findByParticipanteId(Long usuarioId);
    Optional<Conversacion> findByParticipantes(Long usuarioId1, Long usuarioId2);
}
