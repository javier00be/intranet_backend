package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Estudiante;
import java.util.List;
import java.util.Optional;

public interface EstudianteUseCase {
    List<Estudiante> getAllEstudiantes();
    Optional<Estudiante> getEstudianteById(Long id);
    List<Estudiante> getByNivelAndGrado(Estudiante.NivelEducativo nivel, Integer grado);
}
