package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.PagoDTO;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Pago;
import com.example.intranet_school.domain.ports.in.PagoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
    private final PagoUseCase pagoUseCase;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> getAllPagos() {
        return ResponseEntity.ok(pagoUseCase.getAllPagos().stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<PagoDTO>> getPagosByEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(pagoUseCase.getPagosByEstudiante(estudianteId).stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<PagoDTO> createPago(@RequestBody PagoDTO dto) {
        return ResponseEntity.ok(toDTO(pagoUseCase.createPago(toDomain(dto))));
    }

    private PagoDTO toDTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setMonto(pago.getMonto());
        dto.setConcepto(pago.getConcepto());
        dto.setFechaPago(pago.getFechaPago());
        dto.setEstado(pago.getEstado());
        dto.setMetodoPago(pago.getMetodoPago());
        if (pago.getEstudiante() != null) {
            dto.setEstudianteId(pago.getEstudiante().getId());
        }
        return dto;
    }

    private Pago toDomain(PagoDTO dto) {
        Pago pago = new Pago();
        pago.setId(dto.getId());
        pago.setMonto(dto.getMonto());
        pago.setConcepto(dto.getConcepto());
        pago.setFechaPago(dto.getFechaPago());
        pago.setEstado(dto.getEstado());
        pago.setMetodoPago(dto.getMetodoPago());
        if (dto.getEstudianteId() != null) {
            Estudiante estudiante = new Estudiante();
            estudiante.setId(dto.getEstudianteId());
            pago.setEstudiante(estudiante);
        }
        return pago;
    }
}
