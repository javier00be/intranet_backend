package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.CursoEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoJpaRepository extends JpaRepository<CursoEntity, Long> {
    List<CursoEntity> findAllByActivoTrue();
    List<CursoEntity> findByProfesorIdAndActivoTrue(Long profesorId);
    List<CursoEntity> findByNivelAndActivoTrue(EstudianteEntity.NivelEducativo nivel);
    List<CursoEntity> findByAñoAndActivoTrue(Integer año);
    long countByActivoTrue();
}