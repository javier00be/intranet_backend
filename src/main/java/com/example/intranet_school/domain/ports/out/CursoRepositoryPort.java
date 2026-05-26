package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import java.util.List;

public interface CursoRepositoryPort {
    List<Curso> findAll();
    List<Curso> findByProfesorId(Long profesorId);
    List<Curso> findByNivelAndGrado(Estudiante.NivelEducativo nivel, Integer grado);
    Curso save(Curso curso);
    void deleteById(Long id);
    void reactivateById(Long id);
    java.util.Optional<Curso> findById(Long id);
}