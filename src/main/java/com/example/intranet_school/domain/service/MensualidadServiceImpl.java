package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Mensualidad;
import com.example.intranet_school.domain.ports.in.MensualidadUseCase;
import com.example.intranet_school.domain.ports.out.MensualidadRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class MensualidadServiceImpl implements MensualidadUseCase {

    private final MensualidadRepositoryPort mensualidadRepositoryPort;

    @Override
    public List<Mensualidad> getAll() {
        return mensualidadRepositoryPort.findAll();
    }

    @Override
    public List<Mensualidad> getByMatricula(Long matriculaId) {
        return mensualidadRepositoryPort.findByMatriculaId(matriculaId);
    }

    @Override
    public List<Mensualidad> getByEstudiante(Long estudianteId) {
        return mensualidadRepositoryPort.findByEstudianteId(estudianteId);
    }

    @Override
    public Mensualidad pagarMensualidad(Long id) {
        Mensualidad mensualidad = mensualidadRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mensualidad no encontrada: " + id));

        if (mensualidad.getEstadoPago() == Mensualidad.EstadoPago.PAGADO) {
            throw new IllegalStateException("La mensualidad ya fue pagada");
        }

        mensualidad.setEstadoPago(Mensualidad.EstadoPago.PAGADO);
        mensualidad.setFechaPago(LocalDateTime.now());
        return mensualidadRepositoryPort.save(mensualidad);
    }

    @Override
    public Mensualidad subirComprobante(Long id, String nroTransaccion, String url) {
        Mensualidad mensualidad = mensualidadRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mensualidad no encontrada: " + id));
        if (mensualidad.getEstadoPago() == Mensualidad.EstadoPago.PAGADO) {
            throw new IllegalStateException("La mensualidad ya fue pagada");
        }
        mensualidad.setNroTransaccion(nroTransaccion);
        mensualidad.setComprobanteUrl(url);
        mensualidad.setEstadoPago(Mensualidad.EstadoPago.EN_REVISION);
        return mensualidadRepositoryPort.save(mensualidad);
    }

    @Override
    public Mensualidad validarPago(Long id) {
        Mensualidad mensualidad = mensualidadRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mensualidad no encontrada: " + id));
        if (mensualidad.getEstadoPago() != Mensualidad.EstadoPago.EN_REVISION) {
            throw new IllegalStateException("Solo se pueden validar mensualidades EN_REVISION");
        }
        mensualidad.setEstadoPago(Mensualidad.EstadoPago.PAGADO);
        mensualidad.setFechaPago(LocalDateTime.now());
        return mensualidadRepositoryPort.save(mensualidad);
    }
}
