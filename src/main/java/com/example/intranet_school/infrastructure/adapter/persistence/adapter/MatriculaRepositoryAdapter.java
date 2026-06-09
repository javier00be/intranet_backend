package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Matricula;
import com.example.intranet_school.domain.ports.out.MatriculaRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.MatriculaEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.MatriculaMapper;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.MatriculaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MatriculaRepositoryAdapter implements MatriculaRepositoryPort {

    private final MatriculaJpaRepository matriculaJpaRepository;
    private final MatriculaMapper matriculaMapper;

    @Override
    public List<Matricula> findAll() {
        return matriculaJpaRepository.findAllWithDetails().stream()
                .map(matriculaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Matricula> findById(Long id) {
        return matriculaJpaRepository.findById(id).map(matriculaMapper::toDomain);
    }

    @Override
    public List<Matricula> findByEstudianteId(Long estudianteId) {
        return matriculaJpaRepository.findByEstudianteId(estudianteId).stream()
                .map(matriculaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Matricula save(Matricula matricula) {
        return matriculaMapper.toDomain(matriculaJpaRepository.save(matriculaMapper.toEntity(matricula)));
    }

    @Override
    public Optional<Matricula> findActivaByUsuarioEmail(String email) {
        return matriculaJpaRepository
                .findByEstudianteUsuarioEmailAndEstado(email, MatriculaEntity.Estado.ACTIVA)
                .map(matriculaMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        matriculaJpaRepository.deleteById(id);
    }
}
