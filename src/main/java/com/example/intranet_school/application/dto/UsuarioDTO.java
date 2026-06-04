package com.example.intranet_school.application.dto;

import com.example.intranet_school.domain.model.Usuario.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private String apellidoMaterno;
    private Rol rol;
    private String avatar;
}