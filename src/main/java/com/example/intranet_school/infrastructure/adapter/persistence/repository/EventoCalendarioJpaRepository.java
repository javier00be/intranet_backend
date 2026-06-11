package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.EventoCalendarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoCalendarioJpaRepository extends JpaRepository<EventoCalendarioEntity, Long> {}
