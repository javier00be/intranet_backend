package com.example.intranet_school.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversaciones",
    uniqueConstraints = @UniqueConstraint(columnNames = {"participante1_id", "participante2_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participante1_id", nullable = false)
    private UsuarioEntity participante1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participante2_id", nullable = false)
    private UsuarioEntity participante2;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
    }
}
