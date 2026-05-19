package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.domain.ports.out.ProfesorRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.ProfesorMapper;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.ProfesorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfesorRepositoryAdapter implements ProfesorRepositoryPort {
    private final ProfesorJpaRepository profesorJpaRepository;
    private final ProfesorMapper profesorMapper;

    @Override
    public List<Profesor> findAll() {
        return profesorJpaRepository.findAll().stream()
                .map(profesorMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Profesor save(Profesor profesor) {
        return profesorMapper.toDomain(profesorJpaRepository.save(profesorMapper.toEntity(profesor)));
    }

    @Override
    public void deleteById(Long id) {
        profesorJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Profesor> findById(Long id) {
        return profesorJpaRepository.findById(id).map(profesorMapper::toDomain);
    }
}
