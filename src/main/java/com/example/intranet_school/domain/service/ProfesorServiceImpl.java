package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.domain.ports.in.ProfesorUseCase;
import com.example.intranet_school.domain.ports.out.ProfesorRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProfesorServiceImpl implements ProfesorUseCase {
    private final ProfesorRepositoryPort profesorRepositoryPort;

    @Override
    public List<Profesor> getAllProfesores() {
        return profesorRepositoryPort.findAll();
    }

    @Override
    public Optional<Profesor> getProfesorById(Long id) {
        return profesorRepositoryPort.findById(id);
    }

    @Override
    public Profesor createProfesor(Profesor profesor) {
        return profesorRepositoryPort.save(profesor);
    }

    @Override
    public Profesor updateProfesor(Long id, Profesor profesor) {
        return profesorRepositoryPort.findById(id)
                .map(existing -> {
                    profesor.setId(id);
                    return profesorRepositoryPort.save(profesor);
                }).orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
    }

    @Override
    public void deleteProfesor(Long id) {
        profesorRepositoryPort.deleteById(id);
    }
}
