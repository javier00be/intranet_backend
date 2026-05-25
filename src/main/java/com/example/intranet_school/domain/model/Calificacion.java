package com.example.intranet_school.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calificacion {
    private Long id;
    private Estudiante estudiante;
    private Curso curso;
    private Tarea tarea;
    private Double valor;
    private Tipo tipo;
    private LocalDateTime fecha;
    private String observaciones;
    private Profesor profesor;

    public enum Tipo {
        EXAMEN, TAREA, PARTICIPACION, PROYECTO
    }
}
