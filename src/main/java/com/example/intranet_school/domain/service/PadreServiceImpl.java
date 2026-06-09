package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Padre;
import com.example.intranet_school.domain.ports.in.PadreUseCase;
import com.example.intranet_school.domain.ports.out.EstudianteRepositoryPort;
import com.example.intranet_school.domain.ports.out.PadreRepositoryPort;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PadreServiceImpl implements PadreUseCase {
    private final PadreRepositoryPort padreRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;

    @Override
    public List<Padre> getAllPadres() {
        return padreRepositoryPort.findAll();
    }

    @Override
    public Optional<Padre> getPadreById(Long id) {
        return padreRepositoryPort.findById(id);
    }

    @Override
    public Padre createPadre(Padre padre) {
        return padreRepositoryPort.save(padre);
    }

    @Override
    public Padre updatePadre(Long id, Padre padre) {
        padre.setId(id);
        return padreRepositoryPort.save(padre);
    }

    @Override
    public void deletePadre(Long id) {
        padreRepositoryPort.deleteById(id);
    }

    @Override
    public void reactivatePadre(Long id) {
        padreRepositoryPort.reactivateById(id);
    }

    @Override
    public Padre addHijo(Long padreId, Long estudianteId) {
        Padre padre = padreRepositoryPort.findById(padreId)
                .orElseThrow(() -> new RuntimeException("Padre no encontrado: " + padreId));
        Estudiante estudiante = estudianteRepositoryPort.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + estudianteId));

        if (padre.getHijos() == null) {
            padre.setHijos(new ArrayList<>());
        }
        boolean yaEsHijo = padre.getHijos().stream().anyMatch(h -> h.getId().equals(estudianteId));
        if (!yaEsHijo) {
            padre.getHijos().add(estudiante);
        }
        return padreRepositoryPort.save(padre);
    }

    @Override
    public Optional<Padre> getPadreByUsuarioId(Long usuarioId) {
        return padreRepositoryPort.findByUsuarioId(usuarioId);
    }

    @Override
    public Padre removeHijo(Long padreId, Long estudianteId) {
        Padre padre = padreRepositoryPort.findById(padreId)
                .orElseThrow(() -> new RuntimeException("Padre no encontrado: " + padreId));

        if (padre.getHijos() != null) {
            padre.getHijos().removeIf(h -> h.getId().equals(estudianteId));
        }
        return padreRepositoryPort.save(padre);
    }
}
