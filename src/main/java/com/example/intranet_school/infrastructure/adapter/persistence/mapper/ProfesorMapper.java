package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.ProfesorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfesorMapper {
    private final UsuarioMapper usuarioMapper;

    public Profesor toDomain(ProfesorEntity entity) {
        if (entity == null) return null;
        Profesor domain = new Profesor();
        domain.setId(entity.getId());
        domain.setTelefono(entity.getTelefono());
        domain.setUsuario(usuarioMapper.toDomain(entity.getUsuario()));
        domain.setActivo(entity.isActivo());
        return domain;
    }

    public ProfesorEntity toEntity(Profesor domain) {
        if (domain == null) return null;
        ProfesorEntity entity = new ProfesorEntity();
        entity.setId(domain.getId());
        entity.setTelefono(domain.getTelefono());
        entity.setUsuario(usuarioMapper.toEntity(domain.getUsuario()));
        entity.setActivo(domain.isActivo());
        return entity;
    }
}
