package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.Padre;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.PadreEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PadreMapper {
    private final UsuarioMapper usuarioMapper;
    private final EstudianteMapper estudianteMapper;

    public Padre toDomain(PadreEntity entity) {
        if (entity == null) return null;
        List hijos = entity.getHijos() != null
                ? entity.getHijos().stream().map(estudianteMapper::toDomain).collect(Collectors.toList())
                : new ArrayList<>();
        return Padre.builder()
                .id(entity.getId())
                .usuario(usuarioMapper.toDomain(entity.getUsuario()))
                .telefono(entity.getTelefono())
                .hijos(hijos)
                .build();
    }

    public PadreEntity toEntity(Padre domain) {
        if (domain == null) return null;
        List<EstudianteEntity> hijos = domain.getHijos() != null
                ? domain.getHijos().stream()
                        .map(h -> EstudianteEntity.builder().id(h.getId()).build())
                        .collect(Collectors.toList())
                : new ArrayList<>();
        return PadreEntity.builder()
                .id(domain.getId())
                .usuario(usuarioMapper.toEntity(domain.getUsuario()))
                .telefono(domain.getTelefono())
                .hijos(hijos)
                .build();
    }
}
