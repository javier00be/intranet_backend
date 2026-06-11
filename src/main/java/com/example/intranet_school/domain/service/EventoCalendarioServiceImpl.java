package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.EventoCalendario;
import com.example.intranet_school.domain.ports.in.EventoCalendarioUseCase;
import com.example.intranet_school.domain.ports.out.EventoCalendarioRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EventoCalendarioServiceImpl implements EventoCalendarioUseCase {

    private final EventoCalendarioRepositoryPort repositoryPort;

    @Override
    public List<EventoCalendario> getAll() {
        return repositoryPort.findAll();
    }

    @Override
    public EventoCalendario create(EventoCalendario evento) {
        return repositoryPort.save(evento);
    }

    @Override
    public EventoCalendario update(Long id, EventoCalendario evento) {
        evento.setId(id);
        return repositoryPort.save(evento);
    }

    @Override
    public void delete(Long id) {
        repositoryPort.deleteById(id);
    }
}
