package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Calificacion;
import com.example.intranet_school.domain.ports.in.CalificacionUseCase;
import com.example.intranet_school.domain.ports.out.CalificacionRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CalificacionServiceImpl implements CalificacionUseCase {
    private final CalificacionRepositoryPort calificacionRepositoryPort;

    @Override
    public List<Calificacion> getByEstudianteId(Long estudianteId) {
        return calificacionRepositoryPort.findByEstudianteId(estudianteId);
    }

    @Override
    public List<Calificacion> getByCursoId(Long cursoId) {
        return calificacionRepositoryPort.findByCursoId(cursoId);
    }

    @Override
    public Calificacion save(Calificacion calificacion) {
        return calificacionRepositoryPort.save(calificacion);
    }
}
