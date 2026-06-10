package com.example.intranet_school.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorDTO {
    private Long id;
    private UsuarioDTO usuario;
    private String telefono;
    private Boolean activo;
}