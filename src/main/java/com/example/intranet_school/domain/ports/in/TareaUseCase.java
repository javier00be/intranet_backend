package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Tarea;

import java.util.List;

public interface TareaUseCase {
    List<Tarea> getByCursoId(Long cursoId);
    Tarea save(Tarea tarea);
    void deleteById(Long id);
}
