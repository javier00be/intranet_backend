package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EstudianteJpaRepository extends JpaRepository<EstudianteEntity, Long> {
    List<EstudianteEntity> findByNivel(EstudianteEntity.NivelEducativo nivel);
    List<EstudianteEntity> findByGrado(Integer grado);
    List<EstudianteEntity> findByNivelAndGrado(EstudianteEntity.NivelEducativo nivel, Integer grado);
}
