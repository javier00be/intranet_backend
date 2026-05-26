package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.CursoEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.ProfesorEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CursoMapper {

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
        if (entity.getProfesor() != null) {
            Profesor profesor = new Profesor();
            profesor.setId(entity.getProfesor().getId());
            domain.setProfesor(profesor);
        }
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
        if (domain.getProfesor() != null && domain.getProfesor().getId() != null) {
            entity.setProfesor(ProfesorEntity.builder().id(domain.getProfesor().getId()).build());
        }
        return entity;
    }
}
