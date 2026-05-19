package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.ports.out.EstudianteRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.EstudianteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EstudianteRepositoryAdapter implements EstudianteRepositoryPort {
    private final EstudianteJpaRepository estudianteJpaRepository;

    @Override
    public List<Estudiante> findAll() {
        return estudianteJpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Estudiante> findByNivel(Estudiante.NivelEducativo nivel) {
        return estudianteJpaRepository.findByNivel(
                EstudianteEntity.NivelEducativo.valueOf(nivel.name())).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Estudiante> findByGrado(Integer grado) {
        return estudianteJpaRepository.findByGrado(grado).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Estudiante save(Estudiante estudiante) {
        return mapToDomain(estudianteJpaRepository.save(mapToEntity(estudiante)));
    }

    @Override
    public void deleteById(Long id) {
        estudianteJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Estudiante> findById(Long id) {
        return estudianteJpaRepository.findById(id).map(this::mapToDomain);
    }

    private Estudiante mapToDomain(EstudianteEntity entity) {
        Estudiante domain = new Estudiante();
        domain.setId(entity.getId());
        domain.setFechaNacimiento(entity.getFechaNacimiento());
        domain.setDireccion(entity.getDireccion());
        domain.setTelefono(entity.getTelefono());
        domain.setGrado(entity.getGrado());
        domain.setSeccion(entity.getSeccion());
        if (entity.getNivel() != null) {
            domain.setNivel(Estudiante.NivelEducativo.valueOf(entity.getNivel().name()));
        }
        return domain;
    }

    private EstudianteEntity mapToEntity(Estudiante domain) {
        EstudianteEntity entity = new EstudianteEntity();
        entity.setId(domain.getId());
        entity.setFechaNacimiento(domain.getFechaNacimiento());
        entity.setDireccion(domain.getDireccion());
        entity.setTelefono(domain.getTelefono());
        entity.setGrado(domain.getGrado());
        entity.setSeccion(domain.getSeccion());
        return entity;
    }
}
