package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Profesor;

import java.util.List;
import java.util.Optional;

public interface ProfesorRepositoryPort {
    List<Profesor> findAll();
    Profesor save(Profesor profesor);
    void deleteById(Long id);
    void reactivateById(Long id);
    Optional<Profesor> findById(Long id);
}
