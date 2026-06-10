package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Estudiante;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepositoryPort {
    List<Estudiante> findAll();
    List<Estudiante> findByNivel(Estudiante.NivelEducativo nivel);
    List<Estudiante> findByGrado(Integer grado);
    List<Estudiante> findByNivelAndGrado(Estudiante.NivelEducativo nivel, Integer grado);
    List<Estudiante> findByNivelAndGradoAndSeccion(Estudiante.NivelEducativo nivel, Integer grado, String seccion);
    Estudiante save(Estudiante estudiante);
    void deleteById(Long id);
    Optional<Estudiante> findById(Long id);
    Optional<Estudiante> findByUsuarioEmail(String email);
    long count();
}
