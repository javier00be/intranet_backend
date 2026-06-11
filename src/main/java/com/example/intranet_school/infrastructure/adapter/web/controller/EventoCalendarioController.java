package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.EventoCalendarioDTO;
import com.example.intranet_school.domain.model.EventoCalendario;
import com.example.intranet_school.domain.ports.in.EventoCalendarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoCalendarioController {

    private final EventoCalendarioUseCase eventoUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PROFESOR', 'ESTUDIANTE', 'PADRE')")
    public ResponseEntity<List<EventoCalendarioDTO>> getAll() {
        return ResponseEntity.ok(eventoUseCase.getAll().stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @PostMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<EventoCalendarioDTO> create(@RequestBody EventoCalendarioDTO dto) {
        return ResponseEntity.ok(toDTO(eventoUseCase.create(toDomain(dto))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<EventoCalendarioDTO> update(@PathVariable Long id, @RequestBody EventoCalendarioDTO dto) {
        return ResponseEntity.ok(toDTO(eventoUseCase.update(id, toDomain(dto))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EventoCalendarioDTO toDTO(EventoCalendario e) {
        EventoCalendarioDTO dto = new EventoCalendarioDTO();
        dto.setId(e.getId());
        dto.setTitulo(e.getTitulo());
        dto.setDescripcion(e.getDescripcion());
        dto.setFechaInicio(e.getFechaInicio());
        dto.setFechaFin(e.getFechaFin());
        dto.setColor(e.getColor());
        return dto;
    }

    private EventoCalendario toDomain(EventoCalendarioDTO dto) {
        return EventoCalendario.builder()
                .id(dto.getId())
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .color(dto.getColor())
                .build();
    }
}
