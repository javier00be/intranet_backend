package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.MensualidadDTO;
import com.example.intranet_school.domain.model.Mensualidad;
import com.example.intranet_school.domain.ports.in.MensualidadUseCase;
import com.example.intranet_school.domain.ports.in.PadreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mensualidades")
@RequiredArgsConstructor
public class MensualidadController {

    private final MensualidadUseCase mensualidadUseCase;
    private final PadreUseCase padreUseCase;

    @GetMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<List<MensualidadDTO>> getAll() {
        return ResponseEntity.ok(mensualidadUseCase.getAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/estudiante/{estudianteId}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PADRE')")
    public ResponseEntity<List<MensualidadDTO>> getByEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(mensualidadUseCase.getByEstudiante(estudianteId).stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/matricula/{matriculaId}")
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PADRE', 'ESTUDIANTE')")
    public ResponseEntity<List<MensualidadDTO>> getByMatricula(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(mensualidadUseCase.getByMatricula(matriculaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
    }

    @PatchMapping("/{id}/pagar")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> pagar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(toDTO(mensualidadUseCase.pagarMensualidad(id)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/comprobante")
    @PreAuthorize("hasRole('PADRE')")
    public ResponseEntity<?> subirComprobante(@PathVariable Long id,
                                              @RequestBody Map<String, String> body,
                                              Authentication auth) {
        String nroTransaccion = body.get("nroTransaccion");
        String url = body.get("url");
        if ((nroTransaccion == null || nroTransaccion.isBlank()) && (url == null || url.isBlank())) {
            return ResponseEntity.badRequest().body("Se requiere número de transacción o URL del comprobante");
        }
        return padreUseCase.getPadreByEmail(auth.getName())
                .map(padre -> {
                    boolean esHijo = padre.getHijos() != null && padre.getHijos().stream()
                            .anyMatch(h -> mensualidadUseCase.getByEstudiante(h.getId()).stream()
                                    .anyMatch(m -> m.getId().equals(id)));
                    if (!esHijo) return ResponseEntity.status(403).<Object>build();
                    try {
                        return ResponseEntity.ok(toDTO(mensualidadUseCase.subirComprobante(id, nroTransaccion, url)));
                    } catch (IllegalStateException e) {
                        return ResponseEntity.badRequest().<Object>body(e.getMessage());
                    }
                })
                .orElse(ResponseEntity.status(403).build());
    }

    @PatchMapping("/{id}/validar")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> validarPago(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(toDTO(mensualidadUseCase.validarPago(id)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private MensualidadDTO toDTO(Mensualidad m) {
        MensualidadDTO dto = new MensualidadDTO();
        dto.setId(m.getId());
        dto.setMes(m.getMes() != null ? m.getMes().name() : null);
        dto.setAnio(m.getAño());
        dto.setMonto(m.getMonto());
        dto.setEstadoPago(m.getEstadoPago() != null ? m.getEstadoPago().name() : null);
        dto.setFechaVencimiento(m.getFechaVencimiento());
        dto.setFechaPago(m.getFechaPago());
        dto.setComprobanteUrl(m.getComprobanteUrl());
        dto.setNroTransaccion(m.getNroTransaccion());

        if (m.getMatricula() != null) {
            dto.setMatriculaId(m.getMatricula().getId());
            dto.setGrado(m.getMatricula().getGrado());
            dto.setNivel(m.getMatricula().getNivel() != null ? m.getMatricula().getNivel().name() : null);

            if (m.getMatricula().getEstudiante() != null) {
                dto.setEstudianteId(m.getMatricula().getEstudiante().getId());
                if (m.getMatricula().getEstudiante().getUsuario() != null) {
                    String nombre = m.getMatricula().getEstudiante().getUsuario().getNombre();
                    String ap     = m.getMatricula().getEstudiante().getUsuario().getApellido();
                    String am     = m.getMatricula().getEstudiante().getUsuario().getApellidoMaterno();
                    dto.setEstudianteNombre(nombre + " " + ap + (am != null ? " " + am : ""));
                }
            }
        }

        return dto;
    }
}
