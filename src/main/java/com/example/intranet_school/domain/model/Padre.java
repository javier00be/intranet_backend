package com.example.intranet_school.domain.model;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Padre {
    private Long id;
    private Usuario usuario;
    private String dni;
    private String telefono;
    private List<Estudiante> hijos;
    private boolean activo = true;
}
