package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.Conversacion;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.ConversacionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConversacionMapper {
    private final UsuarioMapper usuarioMapper;

    public Conversacion toDomain(ConversacionEntity entity) {
        if (entity == null) return null;
        return Conversacion.builder()
                .id(entity.getId())
                .participante1(usuarioMapper.toDomain(entity.getParticipante1()))
                .participante2(usuarioMapper.toDomain(entity.getParticipante2()))
                .creadoEn(entity.getCreadoEn())
                .build();
    }

    public ConversacionEntity toEntity(Conversacion domain) {
        if (domain == null) return null;
        return ConversacionEntity.builder()
                .id(domain.getId())
                .participante1(usuarioMapper.toEntity(domain.getParticipante1()))
                .participante2(usuarioMapper.toEntity(domain.getParticipante2()))
                .creadoEn(domain.getCreadoEn())
                .build();
    }
}
