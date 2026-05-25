package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstudianteMapper {
    private final UsuarioMapper usuarioMapper;

    public Estudiante toDomain(EstudianteEntity entity) {
        if (entity == null) return null;
        return Estudiante.builder()
                .id(entity.getId())
                .usuario(usuarioMapper.toDomain(entity.getUsuario()))
                .fechaNacimiento(entity.getFechaNacimiento())
                .direccion(entity.getDireccion())
                .telefono(entity.getTelefono())
                .grado(entity.getGrado())
                .seccion(entity.getSeccion())
                .nivel(entity.getNivel() != null
                        ? Estudiante.NivelEducativo.valueOf(entity.getNivel().name()) : null)
                .build();
    }

    public EstudianteEntity toEntity(Estudiante domain) {
        if (domain == null) return null;
        return EstudianteEntity.builder()
                .id(domain.getId())
                .usuario(usuarioMapper.toEntity(domain.getUsuario()))
                .fechaNacimiento(domain.getFechaNacimiento())
                .direccion(domain.getDireccion())
                .telefono(domain.getTelefono())
                .grado(domain.getGrado())
                .seccion(domain.getSeccion())
                .nivel(domain.getNivel() != null
                        ? EstudianteEntity.NivelEducativo.valueOf(domain.getNivel().name()) : null)
                .build();
    }
}
