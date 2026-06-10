package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.TareaDTO;
import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Tarea;
import com.example.intranet_school.domain.ports.in.CursoUseCase;
import com.example.intranet_school.domain.ports.in.EstudianteUseCase;
import com.example.intranet_school.domain.ports.in.TareaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {
    private final TareaUseCase tareaUseCase;
    private final EstudianteUseCase estudianteUseCase;
    private final CursoUseCase cursoUseCase;

    @GetMapping("/mis-tareas")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<TareaDTO>> getMisTareas(Authentication auth) {
        return estudianteUseCase.getByUsuarioEmail(auth.getName())
                .map(estudiante -> {
                    List<Curso> cursos = cursoUseCase.getCursosByNivelAndGrado(
                            estudiante.getNivel(), estudiante.getGrado());
                    if (estudiante.getSeccion() != null) {
                        cursos = cursos.stream()
                                .filter(c -> estudiante.getSeccion().equals(c.getSeccion()))
                                .collect(Collectors.toList());
                    }
                    List<TareaDTO> tareas = cursos.stream()
                            .flatMap(c -> tareaUseCase.getByCursoId(c.getId()).stream()
                                    .map(t -> toDTO(t, c.getNombre())))
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(tareas);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PROFESOR')")
    public ResponseEntity<List<TareaDTO>> getByCurso(@RequestParam Long cursoId) {
        List<TareaDTO> tareas = tareaUseCase.getByCursoId(cursoId).stream()
                .map(t -> toDTO(t, null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tareas);
    }

    @PostMapping
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<TareaDTO> create(@RequestBody TareaDTO dto) {
        Tarea tarea = Tarea.builder()
                .curso(Curso.builder().id(dto.getCursoId()).build())
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .fechaEntrega(dto.getFechaEntrega())
                .archivoUrl(dto.getArchivoUrl())
                .build();
        Tarea saved = tareaUseCase.save(tarea);
        return ResponseEntity.ok(toDTO(saved, null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROFESOR', 'DIRECTOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tareaUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TareaDTO toDTO(Tarea t, String cursoNombreOverride) {
        return TareaDTO.builder()
                .id(t.getId())
                .cursoId(t.getCurso() != null ? t.getCurso().getId() : null)
                .cursoNombre(cursoNombreOverride != null ? cursoNombreOverride
                        : (t.getCurso() != null ? t.getCurso().getNombre() : null))
                .titulo(t.getTitulo())
                .descripcion(t.getDescripcion())
                .fechaEntrega(t.getFechaEntrega())
                .archivoUrl(t.getArchivoUrl())
                .build();
    }
}
