package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.ports.in.CursoUseCase;
import com.example.intranet_school.domain.ports.out.CursoRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CursoServiceImpl implements CursoUseCase {
    private final CursoRepositoryPort cursoRepositoryPort;

    @Override
    public List<Curso> getAllCursos() {
        return cursoRepositoryPort.findAll();
    }

    @Override
    public Optional<Curso> getCursoById(Long id) {
        return cursoRepositoryPort.findById(id);
    }

    @Override
    public List<Curso> getCursosByProfesor(Long profesorId) {
        return cursoRepositoryPort.findByProfesorId(profesorId);
    }

    @Override
    public Curso createCurso(Curso curso) {
        return cursoRepositoryPort.save(curso);
    }

    @Override
    public Curso updateCurso(Long id, Curso curso) {
        return cursoRepositoryPort.findById(id)
                .map(existing -> {
                    curso.setId(id);
                    return cursoRepositoryPort.save(curso);
                }).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }

    @Override
    public void deleteCurso(Long id) {
        cursoRepositoryPort.deleteById(id);
    }

    @Override
    public void reactivateCurso(Long id) {
        cursoRepositoryPort.reactivateById(id);
    }
}
