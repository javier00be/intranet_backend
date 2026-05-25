package com.example.intranet_school.domain.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {
    private Long id;
    private String nombre;
    private String descripcion;
    private Estudiante.NivelEducativo nivel;
    private Integer grado;
    private String seccion;
    private Profesor profesor;
    private Integer año;
}
