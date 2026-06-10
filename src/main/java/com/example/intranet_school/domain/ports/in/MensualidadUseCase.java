package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Mensualidad;

import java.util.List;

public interface MensualidadUseCase {
    List<Mensualidad> getAll();
    List<Mensualidad> getByMatricula(Long matriculaId);
    List<Mensualidad> getByEstudiante(Long estudianteId);
    Mensualidad pagarMensualidad(Long id);
    Mensualidad subirComprobante(Long id, String nroTransaccion, String url);
    Mensualidad validarPago(Long id);
}
