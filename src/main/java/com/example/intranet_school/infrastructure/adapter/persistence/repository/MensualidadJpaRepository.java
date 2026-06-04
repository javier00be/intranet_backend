package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.MensualidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensualidadJpaRepository extends JpaRepository<MensualidadEntity, Long> {

    @Query("SELECT m FROM MensualidadEntity m JOIN FETCH m.matricula ma JOIN FETCH ma.estudiante e JOIN FETCH e.usuario WHERE ma.id = :matriculaId ORDER BY m.mes")
    List<MensualidadEntity> findByMatriculaId(@Param("matriculaId") Long matriculaId);

    @Query("SELECT m FROM MensualidadEntity m JOIN FETCH m.matricula ma JOIN FETCH ma.estudiante e JOIN FETCH e.usuario ORDER BY e.id, m.año, m.mes")
    List<MensualidadEntity> findAllWithDetails();
}
