package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Calificacion;
import java.util.List;

public interface CalificacionUseCase {
    List<Calificacion> getByEstudianteId(Long estudianteId);
    List<Calificacion> getByCursoId(Long cursoId);
    Calificacion save(Calificacion calificacion);
}
