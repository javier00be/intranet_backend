package com.example.intranet_school.infrastructure.adapter.persistence.mapper;

import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Pago;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.PagoEntity;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public Pago toDomain(PagoEntity entity) {
        if (entity == null) return null;
        Pago domain = new Pago();
        domain.setId(entity.getId());
        domain.setMonto(entity.getMonto());
        domain.setConcepto(entity.getConcepto());
        domain.setFechaPago(entity.getFechaPago());
        domain.setMetodoPago(entity.getMetodoPago());
        if (entity.getEstado() != null) {
            domain.setEstado(Pago.Estado.valueOf(entity.getEstado().name()));
        }
        if (entity.getEstudiante() != null) {
            Estudiante estudiante = new Estudiante();
            estudiante.setId(entity.getEstudiante().getId());
            domain.setEstudiante(estudiante);
        }
        return domain;
    }

    public PagoEntity toEntity(Pago domain) {
        if (domain == null) return null;
        PagoEntity entity = new PagoEntity();
        entity.setId(domain.getId());
        entity.setMonto(domain.getMonto());
        entity.setConcepto(domain.getConcepto());
        entity.setFechaPago(domain.getFechaPago());
        entity.setMetodoPago(domain.getMetodoPago());
        if (domain.getEstado() != null) {
            entity.setEstado(PagoEntity.Estado.valueOf(domain.getEstado().name()));
        }
        if (domain.getEstudiante() != null && domain.getEstudiante().getId() != null) {
            entity.setEstudiante(EstudianteEntity.builder().id(domain.getEstudiante().getId()).build());
        }
        return entity;
    }
}
