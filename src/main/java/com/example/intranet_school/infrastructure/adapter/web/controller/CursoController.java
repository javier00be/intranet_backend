package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.CursoDTO;
import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.domain.ports.in.CursoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {
    private final CursoUseCase cursoUseCase;

    @GetMapping
    public ResponseEntity<List<CursoDTO>> getAllCursos() {
        return ResponseEntity.ok(cursoUseCase.getAllCursos().stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> getCursoById(@PathVariable Long id) {
        return cursoUseCase.getCursoById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CursoDTO> createCurso(@RequestBody CursoDTO dto) {
        return ResponseEntity.ok(toDTO(cursoUseCase.createCurso(toDomain(dto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> updateCurso(@PathVariable Long id, @RequestBody CursoDTO dto) {
        return ResponseEntity.ok(toDTO(cursoUseCase.updateCurso(id, toDomain(dto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoUseCase.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }

    private CursoDTO toDTO(Curso curso) {
        CursoDTO dto = new CursoDTO();
        dto.setId(curso.getId());
        dto.setNombre(curso.getNombre());
        dto.setDescripcion(curso.getDescripcion());
        dto.setNivel(curso.getNivel());
        dto.setGrado(curso.getGrado());
        dto.setSeccion(curso.getSeccion());
        dto.setAño(curso.getAño());
        if (curso.getProfesor() != null) {
            dto.setProfesorId(curso.getProfesor().getId());
            if (curso.getProfesor().getUsuario() != null) {
                dto.setProfesorNombre(curso.getProfesor().getUsuario().getNombre()
                        + " " + curso.getProfesor().getUsuario().getApellido());
            }
        }
        return dto;
    }

    private Curso toDomain(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setId(dto.getId());
        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setNivel(dto.getNivel());
        curso.setGrado(dto.getGrado());
        curso.setSeccion(dto.getSeccion());
        curso.setAño(dto.getAño());
        if (dto.getProfesorId() != null) {
            Profesor profesor = new Profesor();
            profesor.setId(dto.getProfesorId());
            curso.setProfesor(profesor);
        }
        return curso;
    }
}
