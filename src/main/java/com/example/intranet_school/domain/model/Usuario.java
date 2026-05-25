package com.example.intranet_school.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    private Long id;
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private Rol rol;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Rol {
        DIRECTOR, PROFESOR, PADRE, ESTUDIANTE
    }
}
