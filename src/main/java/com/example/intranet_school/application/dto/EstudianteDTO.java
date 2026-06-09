package com.example.intranet_school.application.dto;

import com.example.intranet_school.domain.model.Estudiante.NivelEducativo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDTO {
    private Long id;
    private UsuarioDTO usuario;
    private String dni;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String telefono;
    private Integer grado;
    private String seccion;
    private NivelEducativo nivel;
}