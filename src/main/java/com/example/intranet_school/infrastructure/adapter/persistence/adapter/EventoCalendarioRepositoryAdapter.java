package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.EventoCalendario;
import com.example.intranet_school.domain.ports.out.EventoCalendarioRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EventoCalendarioEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.EventoCalendarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventoCalendarioRepositoryAdapter implements EventoCalendarioRepositoryPort {

    private final EventoCalendarioJpaRepository repo;

    @Override
    public List<EventoCalendario> findAll() {
        return repo.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public EventoCalendario save(EventoCalendario evento) {
        return toDomain(repo.save(toEntity(evento)));
    }

    @Override
    public Optional<EventoCalendario> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private EventoCalendario toDomain(EventoCalendarioEntity e) {
        return EventoCalendario.builder()
                .id(e.getId())
                .titulo(e.getTitulo())
                .descripcion(e.getDescripcion())
                .fechaInicio(e.getFechaInicio())
                .fechaFin(e.getFechaFin())
                .color(e.getColor())
                .build();
    }

    private EventoCalendarioEntity toEntity(EventoCalendario e) {
        return EventoCalendarioEntity.builder()
                .id(e.getId())
                .titulo(e.getTitulo())
                .descripcion(e.getDescripcion())
                .fechaInicio(e.getFechaInicio())
                .fechaFin(e.getFechaFin())
                .color(e.getColor() != null ? e.getColor() : "#6366f1")
                .build();
    }
}
