package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Asistencia;
import java.util.List;

public interface AsistenciaUseCase {
    List<Asistencia> getByEstudianteId(Long estudianteId);
    List<Asistencia> getByCursoId(Long cursoId);
    List<Asistencia> saveAll(List<Asistencia> asistencias);
}
