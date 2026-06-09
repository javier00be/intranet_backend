package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Calificacion;
import com.example.intranet_school.domain.ports.out.CalificacionRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.CalificacionMapper;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.CalificacionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalificacionRepositoryAdapter implements CalificacionRepositoryPort {
    private final CalificacionJpaRepository calificacionJpaRepository;
    private final CalificacionMapper calificacionMapper;

    @Override
    public List<Calificacion> findAll() {
        return calificacionJpaRepository.findAll().stream()
                .map(calificacionMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Calificacion> findByEstudianteId(Long estudianteId) {
        return calificacionJpaRepository.findByEstudianteId(estudianteId).stream()
                .map(calificacionMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Calificacion> findByCursoId(Long cursoId) {
        return calificacionJpaRepository.findByCursoId(cursoId).stream()
                .map(calificacionMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Calificacion> findByProfesorId(Long profesorId) {
        return calificacionJpaRepository.findByProfesorId(profesorId).stream()
                .map(calificacionMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Calificacion save(Calificacion calificacion) {
        return calificacionMapper.toDomain(calificacionJpaRepository.save(calificacionMapper.toEntity(calificacion)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        calificacionJpaRepository.deleteById(id);
    }
}
