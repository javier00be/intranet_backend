package com.example.intranet_school.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes",
    indexes = {
        @Index(name = "idx_mensajes_conversacion", columnList = "conversacion_id"),
        @Index(name = "idx_mensajes_enviado_en", columnList = "enviado_en")
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversacion_id", nullable = false)
    private ConversacionEntity conversacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emisor_id", nullable = false)
    private UsuarioEntity emisor;

    @Column(nullable = false, length = 1000)
    private String contenido;

    @Column(name = "enviado_en", nullable = false)
    private LocalDateTime enviadoEn;

    @Column(nullable = false)
    @Builder.Default
    private Boolean leido = false;

    @PrePersist
    protected void onCreate() {
        enviadoEn = LocalDateTime.now();
    }
}
