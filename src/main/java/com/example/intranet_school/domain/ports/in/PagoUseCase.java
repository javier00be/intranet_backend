package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Pago;
import java.util.List;

public interface PagoUseCase {
    List<Pago> getAllPagos();
    List<Pago> getPagosByEstudiante(Long estudianteId);
    Pago createPago(Pago pago);
    Pago updatePagoStatus(Long id, String estado);
}
