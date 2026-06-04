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
public class MatriculaDTO {
    private Long id;
    private Integer anio;
    private Integer grado;
    private String nivel;
    private String estado;
    private String estadoPago;
    private Double montoMatricula;
    private Double montoMensualidad;
    private LocalDateTime fechaCreacion;
    private Long estudianteId;
    private String estudianteNombre;
}
