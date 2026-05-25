package com.example.intranet_school.domain.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matricula {
    private Long id;
    private Estudiante estudiante;
    private Curso curso;
    private Integer año;
    private Estado estado;

    public enum Estado {
        ACTIVA, INACTIVA, FINALIZADA
    }
}
