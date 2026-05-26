package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.event.PagoRegistradoEvent;
import com.example.intranet_school.domain.model.Pago;
import com.example.intranet_school.domain.ports.in.PagoUseCase;
import com.example.intranet_school.domain.ports.out.EventPublisherPort;
import com.example.intranet_school.domain.ports.out.PagoRepositoryPort;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class PagoServiceImpl implements PagoUseCase {
    private final PagoRepositoryPort pagoRepositoryPort;
    private final EventPublisherPort eventPublisherPort;

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
        Pago saved = pagoRepositoryPort.save(pago);
        if (saved.getEstudiante() != null) {
            eventPublisherPort.publish(new PagoRegistradoEvent(
                    saved.getEstudiante().getId(),
                    saved.getMonto(),
                    saved.getConcepto()
            ));
        }
        return saved;
    }

    @Override
    public Pago updatePagoStatus(Long id, String estado) {
        return null;
    }
}
