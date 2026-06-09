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
        return cursoJpaRepository.findByProfesorIdAndActivoTrue(profesorId).stream()
                .map(cursoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Curso> findByNivelAndGrado(Estudiante.NivelEducativo nivel, Integer grado) {
        return cursoJpaRepository.findByNivelAndActivoTrue(
                EstudianteEntity.NivelEducativo.valueOf(nivel.name())).stream()
                .filter(e -> e.getGrados().contains(grado))
                .map(cursoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Curso save(Curso curso) {
        return cursoMapper.toDomain(cursoJpaRepository.save(cursoMapper.toEntity(curso)));
    }

    @Override
    public void deleteById(Long id) {
        cursoJpaRepository.findById(id).ifPresent(e -> {
            e.setActivo(false);
            cursoJpaRepository.save(e);
        });
    }

    @Override
    public void reactivateById(Long id) {
        cursoJpaRepository.findById(id).ifPresent(e -> {
            e.setActivo(true);
            cursoJpaRepository.save(e);
        });
    }

    @Override
    public Optional<Curso> findById(Long id) {
        return cursoJpaRepository.findById(id).map(cursoMapper::toDomain);
    }

    @Override
    public long count() {
        return cursoJpaRepository.countByActivoTrue();
    }
}
