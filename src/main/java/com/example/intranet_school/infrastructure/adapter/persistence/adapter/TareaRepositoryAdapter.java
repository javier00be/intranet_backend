package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Tarea;
import com.example.intranet_school.domain.ports.out.TareaRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.CursoEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.TareaEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.TareaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TareaRepositoryAdapter implements TareaRepositoryPort {
    private final TareaJpaRepository tareaJpaRepository;

    @Override
    public List<Tarea> findAll() {
        return tareaJpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tarea> findByCursoId(Long cursoId) {
        return tareaJpaRepository.findByCursoId(cursoId).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Tarea save(Tarea tarea) {
        return mapToDomain(tareaJpaRepository.save(mapToEntity(tarea)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        tareaJpaRepository.deleteById(id);
    }

    private Tarea mapToDomain(TareaEntity entity) {
        Curso curso = null;
        if (entity.getCurso() != null) {
            curso = Curso.builder()
                    .id(entity.getCurso().getId())
                    .nombre(entity.getCurso().getNombre())
                    .build();
        }
        return Tarea.builder()
                .id(entity.getId())
                .curso(curso)
                .titulo(entity.getTitulo())
                .descripcion(entity.getDescripcion())
                .fechaEntrega(entity.getFechaEntrega())
                .archivoUrl(entity.getArchivoUrl())
                .build();
    }

    private TareaEntity mapToEntity(Tarea tarea) {
        return TareaEntity.builder()
                .id(tarea.getId())
                .curso(tarea.getCurso() != null ? CursoEntity.builder().id(tarea.getCurso().getId()).build() : null)
                .titulo(tarea.getTitulo())
                .descripcion(tarea.getDescripcion())
                .fechaEntrega(tarea.getFechaEntrega())
                .archivoUrl(tarea.getArchivoUrl())
                .build();
    }
}
