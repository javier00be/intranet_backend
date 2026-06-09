package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Asistencia;
import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.ports.out.AsistenciaRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.AsistenciaEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.CursoEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.AsistenciaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AsistenciaRepositoryAdapter implements AsistenciaRepositoryPort {
    private final AsistenciaJpaRepository asistenciaJpaRepository;

    @Override
    public List<Asistencia> findAll() {
        return asistenciaJpaRepository.findAll().stream()
                .map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Asistencia> findByEstudianteId(Long estudianteId) {
        return asistenciaJpaRepository.findByEstudianteId(estudianteId).stream()
                .map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Asistencia> findByCursoId(Long cursoId) {
        return asistenciaJpaRepository.findByCursoId(cursoId).stream()
                .map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Asistencia> findByFecha(LocalDate fecha) {
        return asistenciaJpaRepository.findByFecha(fecha).stream()
                .map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Asistencia save(Asistencia asistencia) {
        return mapToDomain(asistenciaJpaRepository.save(mapToEntity(asistencia)));
    }

    @Override
    @Transactional
    public List<Asistencia> saveAll(List<Asistencia> asistencias) {
        return asistenciaJpaRepository.saveAll(
                asistencias.stream().map(this::mapToEntity).collect(Collectors.toList())
        ).stream().map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        asistenciaJpaRepository.deleteById(id);
    }

    private Asistencia mapToDomain(AsistenciaEntity entity) {
        Estudiante estudiante = entity.getEstudiante() != null
                ? Estudiante.builder().id(entity.getEstudiante().getId()).build() : null;
        Curso curso = entity.getCurso() != null
                ? Curso.builder().id(entity.getCurso().getId()).nombre(entity.getCurso().getNombre()).build() : null;
        return Asistencia.builder()
                .id(entity.getId())
                .estudiante(estudiante)
                .curso(curso)
                .fecha(entity.getFecha())
                .presente(entity.getPresente())
                .observaciones(entity.getObservaciones())
                .build();
    }

    private AsistenciaEntity mapToEntity(Asistencia domain) {
        return AsistenciaEntity.builder()
                .id(domain.getId())
                .estudiante(domain.getEstudiante() != null
                        ? EstudianteEntity.builder().id(domain.getEstudiante().getId()).build() : null)
                .curso(domain.getCurso() != null
                        ? CursoEntity.builder().id(domain.getCurso().getId()).build() : null)
                .fecha(domain.getFecha())
                .presente(domain.getPresente())
                .observaciones(domain.getObservaciones())
                .build();
    }
}
