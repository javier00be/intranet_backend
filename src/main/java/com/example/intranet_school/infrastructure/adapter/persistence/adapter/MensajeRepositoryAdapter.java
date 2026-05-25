package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Mensaje;
import com.example.intranet_school.domain.ports.out.MensajeRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.MensajeMapper;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.MensajeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MensajeRepositoryAdapter implements MensajeRepositoryPort {
    private final MensajeJpaRepository mensajeJpaRepository;
    private final MensajeMapper mensajeMapper;

    @Override
    public Mensaje save(Mensaje mensaje) {
        return mensajeMapper.toDomain(mensajeJpaRepository.save(mensajeMapper.toEntity(mensaje)));
    }

    @Override
    public List<Mensaje> findByConversacionId(Long conversacionId) {
        return mensajeJpaRepository.findByConversacionIdOrderByEnviadoEnAsc(conversacionId).stream()
                .map(mensajeMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteByEnviadoEnBefore(LocalDateTime fecha) {
        mensajeJpaRepository.deleteByEnviadoEnBefore(fecha);
    }

    @Override
    public void marcarComoLeido(Long conversacionId, Long usuarioId) {
        mensajeJpaRepository.marcarComoLeido(conversacionId, usuarioId);
    }
}
