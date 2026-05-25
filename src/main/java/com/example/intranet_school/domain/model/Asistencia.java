package com.example.intranet_school.domain.model;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asistencia {
    private Long id;
    private Estudiante estudiante;
    private Curso curso;
    private LocalDate fecha;
    private Boolean presente;
    private String observaciones;
}
