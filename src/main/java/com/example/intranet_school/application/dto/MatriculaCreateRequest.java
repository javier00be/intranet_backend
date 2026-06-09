package com.example.intranet_school.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaCreateRequest {

    // Datos del padre (uno para todos los hijos)
    private String padreNombre;
    private String padreApellidoPaterno;
    private String padreApellidoMaterno;
    private String padreDni;
    private String padreEmail;
    private String padrePassword;
    private String padreTelefono;

    // Día del mes en que vence cada mensualidad (1-31, se ajusta al último día del mes si excede)
    private Integer diaPago;

    // Lista de alumnos a matricular
    private List<AlumnoMatriculaRequest> alumnos;
}
