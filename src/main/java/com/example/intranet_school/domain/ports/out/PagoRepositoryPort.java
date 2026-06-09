package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Pago;

import java.util.List;

public interface PagoRepositoryPort {
    List<Pago> findAll();
    List<Pago> findByEstudianteId(Long estudianteId);
    List<Pago> findByEstado(Pago.Estado estado);
    Pago save(Pago pago);
    void deleteById(Long id);
    long countByEstado(Pago.Estado estado);
}
