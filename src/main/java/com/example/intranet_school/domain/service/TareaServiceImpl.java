package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Tarea;
import com.example.intranet_school.domain.ports.in.TareaUseCase;
import com.example.intranet_school.domain.ports.out.TareaRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TareaServiceImpl implements TareaUseCase {
    private final TareaRepositoryPort tareaRepositoryPort;

    @Override
    public List<Tarea> getByCursoId(Long cursoId) {
        return tareaRepositoryPort.findByCursoId(cursoId);
    }

    @Override
    public Tarea save(Tarea tarea) {
        return tareaRepositoryPort.save(tarea);
    }

    @Override
    public void deleteById(Long id) {
        tareaRepositoryPort.deleteById(id);
    }
}
