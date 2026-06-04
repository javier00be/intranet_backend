package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Matricula;
import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.MatriculaEntity;
import org.springframework.stereotype.Component;

@Component
public class MatriculaMapper {

    public Matricula toDomain(MatriculaEntity entity) {
        if (entity == null) return null;
        Matricula matricula = new Matricula();
        matricula.setId(entity.getId());
        matricula.setAño(entity.getAño());
        matricula.setGrado(entity.getGrado());
        matricula.setFechaCreacion(entity.getFechaCreacion());
        matricula.setMontoMatricula(entity.getMontoMatricula());
        matricula.setMontoMensualidad(entity.getMontoMensualidad());

        if (entity.getEstado() != null) {
            matricula.setEstado(Matricula.Estado.valueOf(entity.getEstado().name()));
        }
        if (entity.getEstadoPago() != null) {
            matricula.setEstadoPago(Matricula.EstadoPago.valueOf(entity.getEstadoPago().name()));
        }
        if (entity.getNivel() != null) {
            matricula.setNivel(Matricula.NivelEducativo.valueOf(entity.getNivel().name()));
        }
        if (entity.getEstudiante() != null) {
            Estudiante e = new Estudiante();
            e.setId(entity.getEstudiante().getId());
            e.setGrado(entity.getEstudiante().getGrado());
            if (entity.getEstudiante().getNivel() != null) {
                e.setNivel(Estudiante.NivelEducativo.valueOf(entity.getEstudiante().getNivel().name()));
            }
            if (entity.getEstudiante().getUsuario() != null) {
                Usuario u = new Usuario();
                u.setId(entity.getEstudiante().getUsuario().getId());
                u.setNombre(entity.getEstudiante().getUsuario().getNombre());
                u.setApellido(entity.getEstudiante().getUsuario().getApellido());
                u.setApellidoMaterno(entity.getEstudiante().getUsuario().getApellidoMaterno());
                e.setUsuario(u);
            }
            matricula.setEstudiante(e);
        }
        return matricula;
    }

    public MatriculaEntity toEntity(Matricula domain) {
        if (domain == null) return null;
        MatriculaEntity entity = new MatriculaEntity();
        entity.setId(domain.getId());
        entity.setAño(domain.getAño());
        entity.setGrado(domain.getGrado());
        entity.setMontoMatricula(domain.getMontoMatricula());
        entity.setMontoMensualidad(domain.getMontoMensualidad());

        if (domain.getEstado() != null) {
            entity.setEstado(MatriculaEntity.Estado.valueOf(domain.getEstado().name()));
        }
        if (domain.getEstadoPago() != null) {
            entity.setEstadoPago(MatriculaEntity.EstadoPago.valueOf(domain.getEstadoPago().name()));
        }
        if (domain.getNivel() != null) {
            entity.setNivel(EstudianteEntity.NivelEducativo.valueOf(domain.getNivel().name()));
        }
        if (domain.getEstudiante() != null) {
            entity.setEstudiante(EstudianteEntity.builder().id(domain.getEstudiante().getId()).build());
        }
        return entity;
    }
}
