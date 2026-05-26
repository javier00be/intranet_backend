package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.PadreDTO;
import com.example.intranet_school.application.dto.UsuarioDTO;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Padre;
import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.domain.ports.in.PadreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/padres")
@RequiredArgsConstructor
public class PadreController {
    private final PadreUseCase padreUseCase;

    @GetMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<List<PadreDTO>> getAllPadres() {
        return ResponseEntity.ok(padreUseCase.getAllPadres().stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PADRE')")
    public ResponseEntity<PadreDTO> getPadreById(@PathVariable Long id) {
        return padreUseCase.getPadreById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<PadreDTO> createPadre(@RequestBody PadreDTO dto) {
        return ResponseEntity.ok(toDTO(padreUseCase.createPadre(toDomain(dto))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<PadreDTO> updatePadre(@PathVariable Long id, @RequestBody PadreDTO dto) {
        return ResponseEntity.ok(toDTO(padreUseCase.updatePadre(id, toDomain(dto))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<Void> deletePadre(@PathVariable Long id) {
        padreUseCase.deletePadre(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reactivar")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<Void> reactivatePadre(@PathVariable Long id) {
        padreUseCase.reactivatePadre(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{padreId}/hijos/{estudianteId}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<PadreDTO> addHijo(@PathVariable Long padreId, @PathVariable Long estudianteId) {
        return ResponseEntity.ok(toDTO(padreUseCase.addHijo(padreId, estudianteId)));
    }

    @DeleteMapping("/{padreId}/hijos/{estudianteId}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<PadreDTO> removeHijo(@PathVariable Long padreId, @PathVariable Long estudianteId) {
        return ResponseEntity.ok(toDTO(padreUseCase.removeHijo(padreId, estudianteId)));
    }

    private PadreDTO toDTO(Padre padre) {
        UsuarioDTO usuarioDTO = null;
        if (padre.getUsuario() != null) {
            Usuario u = padre.getUsuario();
            usuarioDTO = UsuarioDTO.builder()
                    .id(u.getId())
                    .email(u.getEmail())
                    .nombre(u.getNombre())
                    .apellido(u.getApellido())
                    .rol(u.getRol())
                    .avatar(u.getAvatar())
                    .build();
        }
        List<Long> hijoIds = padre.getHijos() != null
                ? padre.getHijos().stream().map(Estudiante::getId).collect(Collectors.toList())
                : List.of();
        return PadreDTO.builder()
                .id(padre.getId())
                .usuario(usuarioDTO)
                .telefono(padre.getTelefono())
                .hijoIds(hijoIds)
                .activo(padre.isActivo())
                .build();
    }

    private Padre toDomain(PadreDTO dto) {
        Padre padre = new Padre();
        padre.setId(dto.getId());
        padre.setTelefono(dto.getTelefono());
        if (dto.getUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuario().getId());
            usuario.setEmail(dto.getUsuario().getEmail());
            usuario.setNombre(dto.getUsuario().getNombre());
            usuario.setApellido(dto.getUsuario().getApellido());
            usuario.setRol(dto.getUsuario().getRol());
            padre.setUsuario(usuario);
        }
        if (dto.getHijoIds() != null) {
            padre.setHijos(dto.getHijoIds().stream().map(id -> {
                Estudiante e = new Estudiante();
                e.setId(id);
                return e;
            }).collect(Collectors.toList()));
        }
        return padre;
    }
}
