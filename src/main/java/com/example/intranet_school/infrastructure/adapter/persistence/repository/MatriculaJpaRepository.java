package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatriculaJpaRepository extends JpaRepository<MatriculaEntity, Long> {

    List<MatriculaEntity> findByEstudianteId(Long estudianteId);

    @Query("SELECT m FROM MatriculaEntity m JOIN FETCH m.estudiante e JOIN FETCH e.usuario")
    List<MatriculaEntity> findAllWithDetails();
}
