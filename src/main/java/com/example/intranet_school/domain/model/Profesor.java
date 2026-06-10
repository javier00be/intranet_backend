package com.example.intranet_school.domain.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profesor {
    private Long id;
    private Usuario usuario;
    private String telefono;
    private boolean activo = true;
}
