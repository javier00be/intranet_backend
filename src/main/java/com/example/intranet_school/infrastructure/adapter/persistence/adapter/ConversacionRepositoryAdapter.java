package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Conversacion;
import com.example.intranet_school.domain.ports.out.ConversacionRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.ConversacionMapper;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.ConversacionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConversacionRepositoryAdapter implements ConversacionRepositoryPort {
    private final ConversacionJpaRepository conversacionJpaRepository;
    private final ConversacionMapper conversacionMapper;

    @Override
    public Conversacion save(Conversacion conversacion) {
        return conversacionMapper.toDomain(
                conversacionJpaRepository.save(conversacionMapper.toEntity(conversacion)));
    }

    @Override
    public Optional<Conversacion> findById(Long id) {
        return conversacionJpaRepository.findById(id).map(conversacionMapper::toDomain);
    }

    @Override
    public List<Conversacion> findByParticipanteId(Long usuarioId) {
        return conversacionJpaRepository.findByParticipanteId(usuarioId).stream()
                .map(conversacionMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Conversacion> findByParticipantes(Long usuarioId1, Long usuarioId2) {
        return conversacionJpaRepository.findByParticipantes(usuarioId1, usuarioId2)
                .map(conversacionMapper::toDomain);
    }
}
