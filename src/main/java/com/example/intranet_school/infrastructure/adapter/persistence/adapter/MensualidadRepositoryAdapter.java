package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Mensualidad;
import com.example.intranet_school.domain.ports.out.MensualidadRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.MensualidadMapper;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.MensualidadJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MensualidadRepositoryAdapter implements MensualidadRepositoryPort {

    private final MensualidadJpaRepository mensualidadJpaRepository;
    private final MensualidadMapper mensualidadMapper;

    @Override
    public List<Mensualidad> findAll() {
        return mensualidadJpaRepository.findAllWithDetails().stream()
                .map(mensualidadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Mensualidad> findByMatriculaId(Long matriculaId) {
        return mensualidadJpaRepository.findByMatriculaId(matriculaId).stream()
                .map(mensualidadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Mensualidad> findById(Long id) {
        return mensualidadJpaRepository.findById(id).map(mensualidadMapper::toDomain);
    }

    @Override
    public Mensualidad save(Mensualidad mensualidad) {
        return mensualidadMapper.toDomain(
                mensualidadJpaRepository.save(mensualidadMapper.toEntity(mensualidad))
        );
    }

    @Override
    public List<Mensualidad> saveAll(List<Mensualidad> mensualidades) {
        return mensualidadJpaRepository.saveAll(
                mensualidades.stream().map(mensualidadMapper::toEntity).collect(Collectors.toList())
        ).stream().map(mensualidadMapper::toDomain).collect(Collectors.toList());
    }
}
