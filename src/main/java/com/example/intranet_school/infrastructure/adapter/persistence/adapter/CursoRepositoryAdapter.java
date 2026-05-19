package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.ports.out.CursoRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.CursoMapper;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.CursoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CursoRepositoryAdapter implements CursoRepositoryPort {
    private final CursoJpaRepository cursoJpaRepository;
    private final CursoMapper cursoMapper;

    @Override
    public List<Curso> findAll() {
        return cursoJpaRepository.findAll().stream()
                .map(cursoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Curso> findByProfesorId(Long profesorId) {
        return cursoJpaRepository.findByProfesorId(profesorId).stream()
                .map(cursoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Curso> findByNivelAndGrado(Estudiante.NivelEducativo nivel, Integer grado) {
        return cursoJpaRepository.findByNivelAndGrado(
                EstudianteEntity.NivelEducativo.valueOf(nivel.name()), grado).stream()
                .map(cursoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Curso save(Curso curso) {
        return cursoMapper.toDomain(cursoJpaRepository.save(cursoMapper.toEntity(curso)));
    }

    @Override
    public void deleteById(Long id) {
        cursoJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Curso> findById(Long id) {
        return cursoJpaRepository.findById(id).map(cursoMapper::toDomain);
    }
}
