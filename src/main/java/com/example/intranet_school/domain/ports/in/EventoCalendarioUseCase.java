package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.EventoCalendario;
import java.util.List;

public interface EventoCalendarioUseCase {
    List<EventoCalendario> getAll();
    EventoCalendario create(EventoCalendario evento);
    EventoCalendario update(Long id, EventoCalendario evento);
    void delete(Long id);
}
