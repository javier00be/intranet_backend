package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Mensualidad;

import java.util.List;
import java.util.Optional;

public interface MensualidadRepositoryPort {
    List<Mensualidad> findAll();
    List<Mensualidad> findByMatriculaId(Long matriculaId);
    Optional<Mensualidad> findById(Long id);
    Mensualidad save(Mensualidad mensualidad);
    List<Mensualidad> saveAll(List<Mensualidad> mensualidades);
}
