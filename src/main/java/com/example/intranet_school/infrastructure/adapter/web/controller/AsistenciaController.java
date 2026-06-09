package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.AsistenciaDTO;
import com.example.intranet_school.domain.model.Asistencia;
import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.ports.in.AsistenciaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {
    private final AsistenciaUseCase asistenciaUseCase;

    @PostMapping("/batch")
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<List<AsistenciaDTO>> saveBatch(@RequestBody List<AsistenciaDTO> dtos) {
        List<Asistencia> asistencias = dtos.stream().map(dto -> Asistencia.builder()
                .estudiante(Estudiante.builder().id(dto.getEstudianteId()).build())
                .curso(Curso.builder().id(dto.getCursoId()).build())
                .fecha(dto.getFecha())
                .presente(dto.getPresente())
                .observaciones(dto.getObservaciones())
                .build()).collect(Collectors.toList());
        return ResponseEntity.ok(asistenciaUseCase.saveAll(asistencias).stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PROFESOR', 'PADRE')")
    public ResponseEntity<List<AsistenciaDTO>> getAsistencias(
            @RequestParam(required = false) Long estudianteId,
            @RequestParam(required = false) Long cursoId) {
        List<Asistencia> result;
        if (estudianteId != null) {
            result = asistenciaUseCase.getByEstudianteId(estudianteId);
        } else if (cursoId != null) {
            result = asistenciaUseCase.getByCursoId(cursoId);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result.stream().map(this::toDTO).collect(Collectors.toList()));
    }

    private AsistenciaDTO toDTO(Asistencia a) {
        return AsistenciaDTO.builder()
                .id(a.getId())
                .estudianteId(a.getEstudiante() != null ? a.getEstudiante().getId() : null)
                .cursoId(a.getCurso() != null ? a.getCurso().getId() : null)
                .cursoNombre(a.getCurso() != null ? a.getCurso().getNombre() : null)
                .fecha(a.getFecha())
                .presente(a.getPresente())
                .observaciones(a.getObservaciones())
                .build();
    }
}
