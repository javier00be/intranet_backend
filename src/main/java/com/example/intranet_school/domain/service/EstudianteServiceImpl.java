package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.ports.in.EstudianteUseCase;
import com.example.intranet_school.domain.ports.out.EstudianteRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteUseCase {
    private final EstudianteRepositoryPort estudianteRepositoryPort;

    @Override
    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepositoryPort.findAll();
    }

    @Override
    public Optional<Estudiante> getEstudianteById(Long id) {
        return estudianteRepositoryPort.findById(id);
    }

    @Override
    public Optional<Estudiante> getByUsuarioEmail(String email) {
        return estudianteRepositoryPort.findByUsuarioEmail(email);
    }

    @Override
    public List<Estudiante> getByNivelAndGrado(Estudiante.NivelEducativo nivel, Integer grado) {
        return estudianteRepositoryPort.findByNivelAndGrado(nivel, grado);
    }

    @Override
    public List<Estudiante> getByNivelGradoAndSeccion(Estudiante.NivelEducativo nivel, Integer grado, String seccion) {
        return estudianteRepositoryPort.findByNivelAndGradoAndSeccion(nivel, grado, seccion);
    }
}
