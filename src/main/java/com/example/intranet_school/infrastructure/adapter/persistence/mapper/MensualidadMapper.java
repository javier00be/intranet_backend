package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Matricula;
import com.example.intranet_school.domain.model.Mensualidad;
import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.MatriculaEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.MensualidadEntity;
import org.springframework.stereotype.Component;

@Component
public class MensualidadMapper {

    public Mensualidad toDomain(MensualidadEntity entity) {
        if (entity == null) return null;

        Mensualidad m = new Mensualidad();
        m.setId(entity.getId());
        m.setAño(entity.getAño());
        m.setMonto(entity.getMonto());
        m.setFechaVencimiento(entity.getFechaVencimiento());
        m.setFechaPago(entity.getFechaPago());

        if (entity.getMes() != null) {
            m.setMes(Mensualidad.Mes.valueOf(entity.getMes().name()));
        }
        if (entity.getEstadoPago() != null) {
            m.setEstadoPago(Mensualidad.EstadoPago.valueOf(entity.getEstadoPago().name()));
        }

        if (entity.getMatricula() != null) {
            Matricula mat = new Matricula();
            mat.setId(entity.getMatricula().getId());
            mat.setAño(entity.getMatricula().getAño());
            mat.setGrado(entity.getMatricula().getGrado());
            mat.setMontoMensualidad(entity.getMatricula().getMontoMensualidad());
            if (entity.getMatricula().getNivel() != null) {
                mat.setNivel(Matricula.NivelEducativo.valueOf(entity.getMatricula().getNivel().name()));
            }
            if (entity.getMatricula().getEstudiante() != null) {
                Estudiante est = new Estudiante();
                est.setId(entity.getMatricula().getEstudiante().getId());
                if (entity.getMatricula().getEstudiante().getUsuario() != null) {
                    Usuario u = new Usuario();
                    u.setId(entity.getMatricula().getEstudiante().getUsuario().getId());
                    u.setNombre(entity.getMatricula().getEstudiante().getUsuario().getNombre());
                    u.setApellido(entity.getMatricula().getEstudiante().getUsuario().getApellido());
                    u.setApellidoMaterno(entity.getMatricula().getEstudiante().getUsuario().getApellidoMaterno());
                    est.setUsuario(u);
                }
                mat.setEstudiante(est);
            }
            m.setMatricula(mat);
        }

        return m;
    }

    public MensualidadEntity toEntity(Mensualidad domain) {
        if (domain == null) return null;

        MensualidadEntity e = new MensualidadEntity();
        e.setId(domain.getId());
        e.setAño(domain.getAño());
        e.setMonto(domain.getMonto());
        e.setFechaVencimiento(domain.getFechaVencimiento());
        e.setFechaPago(domain.getFechaPago());

        if (domain.getMes() != null) {
            e.setMes(MensualidadEntity.Mes.valueOf(domain.getMes().name()));
        }
        if (domain.getEstadoPago() != null) {
            e.setEstadoPago(MensualidadEntity.EstadoPago.valueOf(domain.getEstadoPago().name()));
        }
        if (domain.getMatricula() != null) {
            e.setMatricula(MatriculaEntity.builder().id(domain.getMatricula().getId()).build());
        }

        return e;
    }
}
