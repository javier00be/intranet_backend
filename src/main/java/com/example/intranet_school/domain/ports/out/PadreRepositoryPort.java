package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Padre;
import java.util.List;
import java.util.Optional;

public interface PadreRepositoryPort {
    List<Padre> findAll();
    Optional<Padre> findById(Long id);
    Optional<Padre> findByUsuarioId(Long usuarioId);
    Optional<Padre> findByUsuarioEmail(String email);
    List<Padre> findByHijoId(Long estudianteId);
    Padre save(Padre padre);
    void deleteById(Long id);
    void reactivateById(Long id);
}
