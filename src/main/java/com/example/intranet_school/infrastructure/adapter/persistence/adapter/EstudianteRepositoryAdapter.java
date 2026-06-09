package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.domain.ports.out.EstudianteRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.UsuarioEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.EstudianteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    public List<Estudiante> findByNivelAndGrado(Estudiante.NivelEducativo nivel, Integer grado) {
        return estudianteJpaRepository.findByNivelAndGrado(
                EstudianteEntity.NivelEducativo.valueOf(nivel.name()), grado).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Estudiante save(Estudiante estudiante) {
        return mapToDomain(estudianteJpaRepository.save(mapToEntity(estudiante)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        estudianteJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Estudiante> findById(Long id) {
        return estudianteJpaRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public long count() {
        return estudianteJpaRepository.count();
    }

    private Estudiante mapToDomain(EstudianteEntity entity) {
        Estudiante domain = new Estudiante();
        domain.setId(entity.getId());
        domain.setDni(entity.getDni());
        domain.setFechaNacimiento(entity.getFechaNacimiento());
        domain.setDireccion(entity.getDireccion());
        domain.setTelefono(entity.getTelefono());
        domain.setGrado(entity.getGrado());
        domain.setSeccion(entity.getSeccion());
        if (entity.getNivel() != null) {
            domain.setNivel(Estudiante.NivelEducativo.valueOf(entity.getNivel().name()));
        }
        if (entity.getUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(entity.getUsuario().getId());
            usuario.setNombre(entity.getUsuario().getNombre());
            usuario.setApellido(entity.getUsuario().getApellido());
            usuario.setEmail(entity.getUsuario().getEmail());
            domain.setUsuario(usuario);
        }
        return domain;
    }

    private EstudianteEntity mapToEntity(Estudiante domain) {
        EstudianteEntity entity = new EstudianteEntity();
        entity.setId(domain.getId());
        entity.setDni(domain.getDni());
        entity.setFechaNacimiento(domain.getFechaNacimiento());
        entity.setDireccion(domain.getDireccion());
        entity.setTelefono(domain.getTelefono());
        entity.setGrado(domain.getGrado());
        entity.setSeccion(domain.getSeccion());
        if (domain.getNivel() != null) {
            entity.setNivel(EstudianteEntity.NivelEducativo.valueOf(domain.getNivel().name()));
        }
        if (domain.getUsuario() != null && domain.getUsuario().getId() != null) {
            entity.setUsuario(UsuarioEntity.builder().id(domain.getUsuario().getId()).build());
        }
        return entity;
    }
}
