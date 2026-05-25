package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.Mensaje;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.ConversacionEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.MensajeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MensajeMapper {
    private final UsuarioMapper usuarioMapper;

    public Mensaje toDomain(MensajeEntity entity) {
        if (entity == null) return null;
        return Mensaje.builder()
                .id(entity.getId())
                .conversacionId(entity.getConversacion() != null ? entity.getConversacion().getId() : null)
                .emisor(usuarioMapper.toDomain(entity.getEmisor()))
                .contenido(entity.getContenido())
                .enviadoEn(entity.getEnviadoEn())
                .leido(entity.getLeido())
                .build();
    }

    public MensajeEntity toEntity(Mensaje domain) {
        if (domain == null) return null;
        return MensajeEntity.builder()
                .id(domain.getId())
                .conversacion(domain.getConversacionId() != null
                        ? ConversacionEntity.builder().id(domain.getConversacionId()).build() : null)
                .emisor(usuarioMapper.toEntity(domain.getEmisor()))
                .contenido(domain.getContenido())
                .enviadoEn(domain.getEnviadoEn())
                .leido(domain.getLeido())
                .build();
    }
}
