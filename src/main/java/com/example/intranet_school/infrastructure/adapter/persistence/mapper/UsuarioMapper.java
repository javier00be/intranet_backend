package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.*;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.*;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        return Usuario.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .apellidoMaterno(entity.getApellidoMaterno())
                .rol(mapRol(entity.getRol()))
                .avatar(entity.getAvatar())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) return null;
        return UsuarioEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .nombre(domain.getNombre())
                .apellido(domain.getApellido())
                .apellidoMaterno(domain.getApellidoMaterno())
                .rol(mapRolToEntity(domain.getRol()))
                .avatar(domain.getAvatar())
                .build();
    }

    private Usuario.Rol mapRol(UsuarioEntity.Rol rol) {
        return rol == null ? null : Usuario.Rol.valueOf(rol.name());
    }

    private UsuarioEntity.Rol mapRolToEntity(Usuario.Rol rol) {
        return rol == null ? null : UsuarioEntity.Rol.valueOf(rol.name());
    }
}