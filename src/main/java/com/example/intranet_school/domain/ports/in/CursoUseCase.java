package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoUseCase {
    List<Curso> getAllCursos();
    Optional<Curso> getCursoById(Long id);
    List<Curso> getCursosByProfesor(Long profesorId);
    Curso createCurso(Curso curso);
    Curso updateCurso(Long id, Curso curso);
    void deleteCurso(Long id);
    void reactivateCurso(Long id);
}
