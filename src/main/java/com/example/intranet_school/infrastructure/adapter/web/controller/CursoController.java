package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.CursoDTO;
import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.domain.ports.in.CursoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {
    private final CursoUseCase cursoUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PROFESOR', 'PADRE', 'ESTUDIANTE')")
    public ResponseEntity<List<CursoDTO>> getAllCursos(
            @RequestParam(required = false) String nivel,
            @RequestParam(required = false) Integer grado) {
        List<Curso> cursos;
        if (nivel != null && grado != null) {
            cursos = cursoUseCase.getCursosByNivelAndGrado(
                    Estudiante.NivelEducativo.valueOf(nivel), grado);
        } else {
            cursos = cursoUseCase.getAllCursos();
        }
        return ResponseEntity.ok(cursos.stream().map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/mis-cursos")
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<List<CursoDTO>> getMisCursos(Authentication auth) {
        return ResponseEntity.ok(cursoUseCase.getCursosByProfesorEmail(auth.getName())
                .stream().map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PROFESOR', 'PADRE', 'ESTUDIANTE')")
    public ResponseEntity<CursoDTO> getCursoById(@PathVariable Long id) {
        return cursoUseCase.getCursoById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<CursoDTO> createCurso(@RequestBody CursoDTO dto) {
        return ResponseEntity.ok(toDTO(cursoUseCase.createCurso(toDomain(dto))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<CursoDTO> updateCurso(@PathVariable Long id, @RequestBody CursoDTO dto) {
        return ResponseEntity.ok(toDTO(cursoUseCase.updateCurso(id, toDomain(dto))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoUseCase.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reactivar")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<Void> reactivateCurso(@PathVariable Long id) {
        cursoUseCase.reactivateCurso(id);
        return ResponseEntity.noContent().build();
    }

    private CursoDTO toDTO(Curso curso) {
        CursoDTO dto = new CursoDTO();
        dto.setId(curso.getId());
        dto.setNombre(curso.getNombre());
        dto.setDescripcion(curso.getDescripcion());
        dto.setNivel(curso.getNivel());
        dto.setGrados(curso.getGrados());
        dto.setSeccion(curso.getSeccion());
        dto.setAño(curso.getAño());
        dto.setActivo(curso.isActivo());
        if (curso.getProfesores() != null) {
            List<Long> ids = new ArrayList<>();
            List<String> nombres = new ArrayList<>();
            for (Profesor p : curso.getProfesores()) {
                ids.add(p.getId());
                if (p.getUsuario() != null) {
                    nombres.add(p.getUsuario().getNombre() + " " + p.getUsuario().getApellido());
                }
            }
            dto.setProfesorIds(ids);
            dto.setProfesorNombres(nombres);
        }
        return dto;
    }

    private Curso toDomain(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setId(dto.getId());
        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setNivel(dto.getNivel());
        curso.setGrados(dto.getGrados());
        curso.setSeccion(dto.getSeccion());
        curso.setAño(dto.getAño());
        curso.setActivo(dto.getActivo() == null || dto.getActivo());
        if (dto.getProfesorIds() != null && !dto.getProfesorIds().isEmpty()) {
            List<Profesor> profesores = dto.getProfesorIds().stream()
                    .map(id -> { Profesor p = new Profesor(); p.setId(id); return p; })
                    .collect(Collectors.toList());
            curso.setProfesores(profesores);
        }
        return curso;
    }
}
