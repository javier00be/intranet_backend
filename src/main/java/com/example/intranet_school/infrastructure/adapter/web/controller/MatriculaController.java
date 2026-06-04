package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.MatriculaCreateRequest;
import com.example.intranet_school.application.dto.MatriculaDTO;
import com.example.intranet_school.domain.model.Matricula;
import com.example.intranet_school.domain.ports.in.MatriculaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaUseCase matriculaUseCase;

    @GetMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<List<MatriculaDTO>> getAllMatriculas() {
        return ResponseEntity.ok(matriculaUseCase.getAllMatriculas().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<MatriculaDTO> getMatriculaById(@PathVariable Long id) {
        return matriculaUseCase.getMatriculaById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> crearMatriculas(@RequestBody MatriculaCreateRequest request) {
        try {
            return ResponseEntity.ok(matriculaUseCase.crearMatriculas(request).stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/pagar")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> pagarMatricula(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(toDTO(matriculaUseCase.pagarMatricula(id)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<Void> deleteMatricula(@PathVariable Long id) {
        matriculaUseCase.deleteMatricula(id);
        return ResponseEntity.noContent().build();
    }

    private MatriculaDTO toDTO(Matricula m) {
        MatriculaDTO dto = new MatriculaDTO();
        dto.setId(m.getId());
        dto.setAnio(m.getAño());
        dto.setGrado(m.getGrado());
        dto.setNivel(m.getNivel() != null ? m.getNivel().name() : null);
        dto.setEstado(m.getEstado() != null ? m.getEstado().name() : null);
        dto.setEstadoPago(m.getEstadoPago() != null ? m.getEstadoPago().name() : null);
        dto.setMontoMatricula(m.getMontoMatricula());
        dto.setMontoMensualidad(m.getMontoMensualidad());
        dto.setFechaCreacion(m.getFechaCreacion());
        if (m.getEstudiante() != null) {
            dto.setEstudianteId(m.getEstudiante().getId());
            if (m.getEstudiante().getUsuario() != null) {
                String nombre = m.getEstudiante().getUsuario().getNombre();
                String ap     = m.getEstudiante().getUsuario().getApellido();
                String am     = m.getEstudiante().getUsuario().getApellidoMaterno();
                dto.setEstudianteNombre(nombre + " " + ap + (am != null ? " " + am : ""));
            }
        }
        return dto;
    }
}
