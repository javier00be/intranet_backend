package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Asistencia;
import com.example.intranet_school.domain.ports.in.AsistenciaUseCase;
import com.example.intranet_school.domain.ports.out.AsistenciaRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AsistenciaServiceImpl implements AsistenciaUseCase {
    private final AsistenciaRepositoryPort asistenciaRepositoryPort;

    @Override
    public List<Asistencia> getByEstudianteId(Long estudianteId) {
        return asistenciaRepositoryPort.findByEstudianteId(estudianteId);
    }

    @Override
    public List<Asistencia> getByCursoId(Long cursoId) {
        return asistenciaRepositoryPort.findByCursoId(cursoId);
    }

    @Override
    public List<Asistencia> saveAll(List<Asistencia> asistencias) {
        return asistenciaRepositoryPort.saveAll(asistencias);
    }
}
