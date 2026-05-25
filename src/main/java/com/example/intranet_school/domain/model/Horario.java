package com.example.intranet_school.domain.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Horario {
    private Long id;
    private Curso curso;
    private Integer dia;
    private String horaInicio;
    private String horaFin;
    private String salon;
}
