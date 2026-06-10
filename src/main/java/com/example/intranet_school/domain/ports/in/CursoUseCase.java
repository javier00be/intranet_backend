package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import java.util.List;
import java.util.Optional;

public interface CursoUseCase {
    List<Curso> getAllCursos();
    Optional<Curso> getCursoById(Long id);
    List<Curso> getCursosByProfesor(Long profesorId);
    List<Curso> getCursosByProfesorEmail(String email);
    List<Curso> getCursosByNivelAndGrado(Estudiante.NivelEducativo nivel, Integer grado);
    Curso createCurso(Curso curso);
    Curso updateCurso(Long id, Curso curso);
    void deleteCurso(Long id);
    void reactivateCurso(Long id);
    void syncCursosForProfesor(Long profesorId, List<Long> cursoIds);
}
