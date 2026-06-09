package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Asistencia;

import java.util.List;

public interface AsistenciaRepositoryPort {
    List<Asistencia> findAll();
    List<Asistencia> findByEstudianteId(Long estudianteId);
    List<Asistencia> findByCursoId(Long cursoId);
    List<Asistencia> findByFecha(java.time.LocalDate fecha);
    Asistencia save(Asistencia asistencia);
    List<Asistencia> saveAll(List<Asistencia> asistencias);
    void deleteById(Long id);
}
