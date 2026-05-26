package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Profesor;
import java.util.List;
import java.util.Optional;

public interface ProfesorUseCase {
    List<Profesor> getAllProfesores();
    Optional<Profesor> getProfesorById(Long id);
    Profesor createProfesor(Profesor profesor);
    Profesor updateProfesor(Long id, Profesor profesor);
    void deleteProfesor(Long id);
    void reactivateProfesor(Long id);
}
