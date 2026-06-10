package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.PadreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PadreJpaRepository extends JpaRepository<PadreEntity, Long> {
    List<PadreEntity> findAllByActivoTrue();
    Optional<PadreEntity> findByUsuarioIdAndActivoTrue(Long usuarioId);
    Optional<PadreEntity> findByUsuarioEmailAndActivoTrue(String email);

    @Query("SELECT p FROM PadreEntity p JOIN p.hijos h WHERE h.id = :estudianteId AND p.activo = true")
    List<PadreEntity> findByHijoId(@Param("estudianteId") Long estudianteId);
}
