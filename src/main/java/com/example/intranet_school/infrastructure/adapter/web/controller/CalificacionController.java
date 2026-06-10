package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.CalificacionDTO;
import com.example.intranet_school.domain.model.Calificacion;
import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.domain.ports.in.CalificacionUseCase;
import com.example.intranet_school.domain.ports.in.EstudianteUseCase;
import com.example.intranet_school.domain.ports.in.ProfesorUseCase;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.CalificacionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calificaciones")
@RequiredArgsConstructor
public class CalificacionController {
    private final CalificacionUseCase calificacionUseCase;
    private final ProfesorUseCase profesorUseCase;
    private final EstudianteUseCase estudianteUseCase;
    private final CalificacionMapper calificacionMapper;

    @PostMapping
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<CalificacionDTO> saveCalificacion(
            @RequestBody CalificacionDTO dto,
            Authentication auth) {
        Profesor profesor = profesorUseCase.getByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
        Calificacion calificacion = Calificacion.builder()
                .estudiante(Estudiante.builder().id(dto.getEstudianteId()).build())
                .curso(Curso.builder().id(dto.getCursoId()).build())
                .profesor(Profesor.builder().id(profesor.getId()).build())
                .valor(dto.getValor())
                .tipo(dto.getTipo() != null ? Calificacion.Tipo.valueOf(dto.getTipo()) : null)
                .fecha(dto.getFecha())
                .observaciones(dto.getObservaciones())
                .build();
        return ResponseEntity.ok(calificacionMapper.toDTO(calificacionUseCase.save(calificacion)));
    }

    @GetMapping("/mis-notas")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<CalificacionDTO>> getMisNotas(Authentication auth) {
        return estudianteUseCase.getByUsuarioEmail(auth.getName())
                .map(e -> ResponseEntity.ok(
                        calificacionUseCase.getByEstudianteId(e.getId()).stream()
                                .map(calificacionMapper::toDTO).collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PROFESOR', 'PADRE')")
    public ResponseEntity<List<CalificacionDTO>> getCalificaciones(
            @RequestParam(required = false) Long estudianteId,
            @RequestParam(required = false) Long cursoId) {
        List<Calificacion> result;
        if (estudianteId != null) {
            result = calificacionUseCase.getByEstudianteId(estudianteId);
        } else if (cursoId != null) {
            result = calificacionUseCase.getByCursoId(cursoId);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result.stream().map(calificacionMapper::toDTO).collect(Collectors.toList()));
    }
}
