package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Pago;
import com.example.intranet_school.domain.ports.in.PagoUseCase;
import com.example.intranet_school.domain.ports.out.PagoRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PagoServiceImpl implements PagoUseCase {
    private final PagoRepositoryPort pagoRepositoryPort;

    @Override
    public List<Pago> getAllPagos() {
        return pagoRepositoryPort.findAll();
    }

    @Override
    public List<Pago> getPagosByEstudiante(Long estudianteId) {
        return pagoRepositoryPort.findByEstudianteId(estudianteId);
    }

    @Override
    public Pago createPago(Pago pago) {
        return pagoRepositoryPort.save(pago);
    }

    @Override
    public Pago updatePagoStatus(Long id, String estado) {
        // En una implementación real, buscaríamos el pago y actualizaríamos su estado
        // Por ahora lo dejamos simplificado para la arquitectura hexagonal
        return null;
    }
}
