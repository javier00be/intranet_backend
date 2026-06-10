package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.EstudianteDTO;
import com.example.intranet_school.application.dto.UsuarioDTO;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.ports.in.EstudianteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    private final EstudianteUseCase estudianteUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PADRE', 'PROFESOR')")
    public ResponseEntity<List<EstudianteDTO>> getAllEstudiantes(
            @RequestParam(required = false) String nivel,
            @RequestParam(required = false) Integer grado,
            @RequestParam(required = false) String seccion) {
        List<Estudiante> result;
        if (nivel != null && grado != null && seccion != null) {
            result = estudianteUseCase.getByNivelGradoAndSeccion(
                    Estudiante.NivelEducativo.valueOf(nivel.toUpperCase()), grado, seccion);
        } else if (nivel != null && grado != null) {
            result = estudianteUseCase.getByNivelAndGrado(
                    Estudiante.NivelEducativo.valueOf(nivel.toUpperCase()), grado);
        } else {
            result = estudianteUseCase.getAllEstudiantes();
        }
        return ResponseEntity.ok(result.stream().map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<EstudianteDTO> getMyProfile(Authentication auth) {
        return estudianteUseCase.getByUsuarioEmail(auth.getName())
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PADRE', 'PROFESOR')")
    public ResponseEntity<EstudianteDTO> getEstudianteById(@PathVariable Long id) {
        return estudianteUseCase.getEstudianteById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private EstudianteDTO toDTO(Estudiante e) {
        UsuarioDTO usuarioDTO = null;
        if (e.getUsuario() != null) {
            usuarioDTO = UsuarioDTO.builder()
                    .id(e.getUsuario().getId())
                    .nombre(e.getUsuario().getNombre())
                    .apellido(e.getUsuario().getApellido())
                    .email(e.getUsuario().getEmail())
                    .build();
        }
        return EstudianteDTO.builder()
                .id(e.getId())
                .usuario(usuarioDTO)
                .dni(e.getDni())
                .fechaNacimiento(e.getFechaNacimiento())
                .direccion(e.getDireccion())
                .telefono(e.getTelefono())
                .grado(e.getGrado())
                .seccion(e.getSeccion())
                .nivel(e.getNivel())
                .build();
    }
}
