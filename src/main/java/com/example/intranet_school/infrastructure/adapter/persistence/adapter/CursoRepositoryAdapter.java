package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.ports.out.CursoRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.CursoEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.ProfesorEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.CursoMapper;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.CursoJpaRepository;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.ProfesorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CursoRepositoryAdapter implements CursoRepositoryPort {
    private final CursoJpaRepository cursoJpaRepository;
    private final CursoMapper cursoMapper;
    private final ProfesorJpaRepository profesorJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        return cursoJpaRepository.findAll().stream()
                .map(cursoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findByProfesorId(Long profesorId) {
        return cursoJpaRepository.findByProfesoresIdAndActivoTrue(profesorId).stream()
                .map(cursoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findByNivelAndGrado(Estudiante.NivelEducativo nivel, Integer grado) {
        return cursoJpaRepository.findByNivelAndActivoTrue(
                EstudianteEntity.NivelEducativo.valueOf(nivel.name())).stream()
                .filter(e -> e.getGrados().contains(grado))
                .map(cursoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        CursoEntity entity = cursoMapper.toEntity(curso);
        // Preserve existing professor assignments when the incoming list is empty (course form updates)
        if (entity.getId() != null && (entity.getProfesores() == null || entity.getProfesores().isEmpty())) {
            cursoJpaRepository.findById(entity.getId())
                    .ifPresent(existing -> entity.setProfesores(existing.getProfesores()));
        }
        return cursoMapper.toDomain(cursoJpaRepository.save(entity));
    }

    @Override
    @Transactional
    public void syncCursosForProfesor(Long profesorId, List<Long> cursoIds) {
        ProfesorEntity profesor = profesorJpaRepository.getReferenceById(profesorId);
        Set<Long> newIds = new HashSet<>(cursoIds);

        List<CursoEntity> current = cursoJpaRepository.findByProfesoresId(profesorId);
        Set<Long> currentIds = current.stream().map(CursoEntity::getId).collect(Collectors.toSet());

        for (CursoEntity curso : current) {
            if (!newIds.contains(curso.getId())) {
                curso.getProfesores().removeIf(p -> p.getId().equals(profesorId));
            }
        }

        for (Long cursoId : cursoIds) {
            if (!currentIds.contains(cursoId)) {
                cursoJpaRepository.findById(cursoId)
                        .ifPresent(c -> c.getProfesores().add(profesor));
            }
        }
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
