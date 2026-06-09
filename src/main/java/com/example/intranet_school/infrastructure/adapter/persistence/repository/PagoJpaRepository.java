package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoJpaRepository extends JpaRepository<PagoEntity, Long> {
    List<PagoEntity> findByEstudianteId(Long estudianteId);
    List<PagoEntity> findByEstado(PagoEntity.Estado estado);
    long countByEstado(PagoEntity.Estado estado);
}
