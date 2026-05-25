package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.ProfesorDTO;
import com.example.intranet_school.application.dto.UsuarioDTO;
import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.domain.ports.in.ProfesorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profesores")
@RequiredArgsConstructor
public class ProfesorController {
    private final ProfesorUseCase profesorUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PROFESOR')")
    public ResponseEntity<List<ProfesorDTO>> getAllProfesores() {
        return ResponseEntity.ok(profesorUseCase.getAllProfesores().stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PROFESOR')")
    public ResponseEntity<ProfesorDTO> getProfesorById(@PathVariable Long id) {
        return profesorUseCase.getProfesorById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<ProfesorDTO> createProfesor(@RequestBody ProfesorDTO dto) {
        return ResponseEntity.ok(toDTO(profesorUseCase.createProfesor(toDomain(dto))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<ProfesorDTO> updateProfesor(@PathVariable Long id, @RequestBody ProfesorDTO dto) {
        return ResponseEntity.ok(toDTO(profesorUseCase.updateProfesor(id, toDomain(dto))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
        profesorUseCase.deleteProfesor(id);
        return ResponseEntity.noContent().build();
    }

    private ProfesorDTO toDTO(Profesor profesor) {
        UsuarioDTO usuarioDTO = null;
        if (profesor.getUsuario() != null) {
            Usuario u = profesor.getUsuario();
            usuarioDTO = UsuarioDTO.builder()
                    .id(u.getId())
                    .email(u.getEmail())
                    .nombre(u.getNombre())
                    .apellido(u.getApellido())
                    .rol(u.getRol())
                    .avatar(u.getAvatar())
                    .build();
        }
        return ProfesorDTO.builder()
                .id(profesor.getId())
                .usuario(usuarioDTO)
                .especialidad(profesor.getEspecialidad())
                .telefono(profesor.getTelefono())
                .build();
    }

    private Profesor toDomain(ProfesorDTO dto) {
        Profesor profesor = new Profesor();
        profesor.setId(dto.getId());
        profesor.setEspecialidad(dto.getEspecialidad());
        profesor.setTelefono(dto.getTelefono());
        if (dto.getUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuario().getId());
            usuario.setEmail(dto.getUsuario().getEmail());
            usuario.setNombre(dto.getUsuario().getNombre());
            usuario.setApellido(dto.getUsuario().getApellido());
            usuario.setRol(dto.getUsuario().getRol());
            profesor.setUsuario(usuario);
        }
        return profesor;
    }
}
