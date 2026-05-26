package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.ProfesorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfesorJpaRepository extends JpaRepository<ProfesorEntity, Long> {
    List<ProfesorEntity> findAllByActivoTrue();
}
