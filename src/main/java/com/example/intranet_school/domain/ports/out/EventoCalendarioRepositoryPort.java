package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.EventoCalendario;
import java.util.List;
import java.util.Optional;

public interface EventoCalendarioRepositoryPort {
    List<EventoCalendario> findAll();
    EventoCalendario save(EventoCalendario evento);
    Optional<EventoCalendario> findById(Long id);
    void deleteById(Long id);
}
