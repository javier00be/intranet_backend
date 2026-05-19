package com.example.intranet_school.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private Long totalEstudiantes;
    private Long totalProfesores;
    private Long totalCursos;
    private Long pagosPendientes;
    private Long misCursos;
    private Long misTareas;
    private Long misAlumnos;
    private Long misNotas;
}
