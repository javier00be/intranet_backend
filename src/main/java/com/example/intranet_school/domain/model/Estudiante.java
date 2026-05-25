package com.example.intranet_school.domain.model;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudiante {
    private Long id;
    private Usuario usuario;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String telefono;
    private Integer grado;
    private String seccion;
    private NivelEducativo nivel;

    public enum NivelEducativo {
        INICIAL, PRIMARIA, SECUNDARIA
    }
}
