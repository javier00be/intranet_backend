package com.example.intranet_school.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionDTO {
    private Long id;
    private Long estudianteId;
    private Long cursoId;
    private String cursoNombre;
    private Double valor;
    private String tipo;
    private LocalDateTime fecha;
    private String observaciones;
    private Long profesorId;
}
