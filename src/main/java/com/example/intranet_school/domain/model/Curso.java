package com.example.intranet_school.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {
    private Long id;
    private String nombre;
    private String descripcion;
    private Estudiante.NivelEducativo nivel;
    @Builder.Default
    private List<Integer> grados = new ArrayList<>();
    private String seccion;
    private Profesor profesor;
    private Integer año;
    private boolean activo = true;
}
