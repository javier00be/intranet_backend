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
public class AlumnoMatriculaRequest {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String email;
    private String password;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;
    private Integer grado;
    private String seccion;
    private String nivel; // INICIAL | PRIMARIA | SECUNDARIA
    private Integer anio;
    private Double montoMatricula;
    private Double montoMensualidad;
}
