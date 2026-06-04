package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Matricula;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepositoryPort {
    List<Matricula> findAll();
    Optional<Matricula> findById(Long id);
    List<Matricula> findByEstudianteId(Long estudianteId);
    Matricula save(Matricula matricula);
    void deleteById(Long id);
}
