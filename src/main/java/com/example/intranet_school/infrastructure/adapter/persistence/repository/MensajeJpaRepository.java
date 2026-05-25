package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.MensajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface MensajeJpaRepository extends JpaRepository<MensajeEntity, Long> {

    List<MensajeEntity> findByConversacionIdOrderByEnviadoEnAsc(Long conversacionId);

    @Modifying
    @Transactional
    @Query("DELETE FROM MensajeEntity m WHERE m.enviadoEn < :fecha")
    void deleteByEnviadoEnBefore(@Param("fecha") LocalDateTime fecha);

    @Modifying
    @Transactional
    @Query("UPDATE MensajeEntity m SET m.leido = true " +
           "WHERE m.conversacion.id = :conversacionId " +
           "AND m.emisor.id <> :usuarioId AND m.leido = false")
    void marcarComoLeido(@Param("conversacionId") Long conversacionId, @Param("usuarioId") Long usuarioId);
}
