package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.CursoEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.ProfesorEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.ProfesorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CursoMapper {

    private final ProfesorJpaRepository profesorJpaRepository;

    public Curso toDomain(CursoEntity entity) {
        if (entity == null) return null;
        Curso domain = new Curso();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setGrados(entity.getGrados() != null ? new ArrayList<>(entity.getGrados()) : new ArrayList<>());
        domain.setSeccion(entity.getSeccion());
        domain.setAño(entity.getAño());
        domain.setActivo(entity.isActivo());
        if (entity.getNivel() != null) {
            domain.setNivel(Estudiante.NivelEducativo.valueOf(entity.getNivel().name()));
        }
        List<Profesor> profesores = new ArrayList<>();
        if (entity.getProfesores() != null) {
            for (ProfesorEntity pe : entity.getProfesores()) {
                Profesor profesor = new Profesor();
                profesor.setId(pe.getId());
                if (pe.getUsuario() != null) {
                    Usuario usuario = new Usuario();
                    usuario.setId(pe.getUsuario().getId());
                    usuario.setNombre(pe.getUsuario().getNombre());
                    usuario.setApellido(pe.getUsuario().getApellido());
                    profesor.setUsuario(usuario);
                }
                profesores.add(profesor);
            }
        }
        domain.setProfesores(profesores);
        return domain;
    }

    public CursoEntity toEntity(Curso domain) {
        if (domain == null) return null;
        CursoEntity entity = new CursoEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setGrados(domain.getGrados() != null ? new ArrayList<>(domain.getGrados()) : new ArrayList<>());
        entity.setSeccion(domain.getSeccion());
        entity.setAño(domain.getAño());
        entity.setActivo(domain.isActivo());
        if (domain.getNivel() != null) {
            entity.setNivel(EstudianteEntity.NivelEducativo.valueOf(domain.getNivel().name()));
        }
        if (domain.getProfesores() != null && !domain.getProfesores().isEmpty()) {
            List<Long> ids = domain.getProfesores().stream()
                    .map(Profesor::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (!ids.isEmpty()) {
                entity.setProfesores(new ArrayList<>(profesorJpaRepository.findAllById(ids)));
            }
        }
        return entity;
    }
}
