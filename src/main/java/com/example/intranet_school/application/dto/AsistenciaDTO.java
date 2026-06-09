package com.example.intranet_school.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaDTO {
    private Long id;
    private Long estudianteId;
    private Long cursoId;
    private String cursoNombre;
    private LocalDate fecha;
    private Boolean presente;
    private String observaciones;
}
