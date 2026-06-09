package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.application.dto.CalificacionDTO;
import com.example.intranet_school.domain.model.Calificacion;
import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.CalificacionEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.CursoEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.ProfesorEntity;
import org.springframework.stereotype.Component;

@Component
public class CalificacionMapper {

    public Calificacion toDomain(CalificacionEntity entity) {
        if (entity == null) return null;
        Estudiante estudiante = entity.getEstudiante() != null
                ? Estudiante.builder().id(entity.getEstudiante().getId()).build() : null;
        Curso curso = entity.getCurso() != null
                ? Curso.builder().id(entity.getCurso().getId()).nombre(entity.getCurso().getNombre()).build() : null;
        Profesor profesor = entity.getProfesor() != null
                ? Profesor.builder().id(entity.getProfesor().getId()).build() : null;
        return Calificacion.builder()
                .id(entity.getId())
                .estudiante(estudiante)
                .curso(curso)
                .valor(entity.getValor())
                .tipo(entity.getTipo() != null ? Calificacion.Tipo.valueOf(entity.getTipo().name()) : null)
                .fecha(entity.getFecha())
                .observaciones(entity.getObservaciones())
                .profesor(profesor)
                .build();
    }

    public CalificacionEntity toEntity(Calificacion domain) {
        if (domain == null) return null;
        return CalificacionEntity.builder()
                .id(domain.getId())
                .estudiante(domain.getEstudiante() != null
                        ? EstudianteEntity.builder().id(domain.getEstudiante().getId()).build() : null)
                .curso(domain.getCurso() != null
                        ? CursoEntity.builder().id(domain.getCurso().getId()).build() : null)
                .valor(domain.getValor())
                .tipo(domain.getTipo() != null ? CalificacionEntity.Tipo.valueOf(domain.getTipo().name()) : null)
                .fecha(domain.getFecha())
                .observaciones(domain.getObservaciones())
                .profesor(domain.getProfesor() != null
                        ? ProfesorEntity.builder().id(domain.getProfesor().getId()).build() : null)
                .build();
    }

    public CalificacionDTO toDTO(Calificacion c) {
        if (c == null) return null;
        return CalificacionDTO.builder()
                .id(c.getId())
                .estudianteId(c.getEstudiante() != null ? c.getEstudiante().getId() : null)
                .cursoId(c.getCurso() != null ? c.getCurso().getId() : null)
                .cursoNombre(c.getCurso() != null ? c.getCurso().getNombre() : null)
                .valor(c.getValor())
                .tipo(c.getTipo() != null ? c.getTipo().name() : null)
                .fecha(c.getFecha())
                .observaciones(c.getObservaciones())
                .profesorId(c.getProfesor() != null ? c.getProfesor().getId() : null)
                .build();
    }
}
