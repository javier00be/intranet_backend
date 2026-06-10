package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.ports.in.CursoUseCase;
import com.example.intranet_school.domain.ports.out.CursoRepositoryPort;
import com.example.intranet_school.domain.ports.out.ProfesorRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CursoServiceImpl implements CursoUseCase {
    private final CursoRepositoryPort cursoRepositoryPort;
    private final ProfesorRepositoryPort profesorRepositoryPort;

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
    public List<Curso> getCursosByProfesorEmail(String email) {
        return profesorRepositoryPort.findByUsuarioEmail(email)
                .map(p -> cursoRepositoryPort.findByProfesorId(p.getId()))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Curso> getCursosByNivelAndGrado(Estudiante.NivelEducativo nivel, Integer grado) {
        return cursoRepositoryPort.findByNivelAndGrado(nivel, grado);
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

    @Override
    public void syncCursosForProfesor(Long profesorId, List<Long> cursoIds) {
        cursoRepositoryPort.syncCursosForProfesor(profesorId, cursoIds);
    }
}
