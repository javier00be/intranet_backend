package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.domain.model.Padre;
import java.util.List;
import java.util.Optional;

public interface PadreUseCase {
    List<Padre> getAllPadres();
    Optional<Padre> getPadreById(Long id);
    Padre createPadre(Padre padre);
    Padre updatePadre(Long id, Padre padre);
    void deletePadre(Long id);
    void reactivatePadre(Long id);
    Padre addHijo(Long padreId, Long estudianteId);
    Padre removeHijo(Long padreId, Long estudianteId);
    Optional<Padre> getPadreByUsuarioId(Long usuarioId);
    Optional<Padre> getPadreByEmail(String email);
}
