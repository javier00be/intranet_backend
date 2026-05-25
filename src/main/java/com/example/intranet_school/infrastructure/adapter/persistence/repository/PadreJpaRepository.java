package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.PadreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PadreJpaRepository extends JpaRepository<PadreEntity, Long> {
    Optional<PadreEntity> findByUsuarioId(Long usuarioId);
}
