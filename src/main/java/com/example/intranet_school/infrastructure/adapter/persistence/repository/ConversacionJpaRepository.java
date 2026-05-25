package com.example.intranet_school.infrastructure.adapter.persistence.repository;

import com.example.intranet_school.infrastructure.adapter.persistence.entity.ConversacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ConversacionJpaRepository extends JpaRepository<ConversacionEntity, Long> {

    @Query("SELECT c FROM ConversacionEntity c WHERE c.participante1.id = :uid OR c.participante2.id = :uid")
    List<ConversacionEntity> findByParticipanteId(@Param("uid") Long usuarioId);

    @Query("SELECT c FROM ConversacionEntity c WHERE " +
           "(c.participante1.id = :id1 AND c.participante2.id = :id2) OR " +
           "(c.participante1.id = :id2 AND c.participante2.id = :id1)")
    Optional<ConversacionEntity> findByParticipantes(@Param("id1") Long id1, @Param("id2") Long id2);
}
