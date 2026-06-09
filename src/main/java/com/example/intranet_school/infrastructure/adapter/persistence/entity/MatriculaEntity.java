package com.example.intranet_school.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matriculas", uniqueConstraints = {
    @UniqueConstraint(name = "uk_matricula_estudiante_anio", columnNames = {"estudiante_id", "año"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatriculaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private EstudianteEntity estudiante;

    private Integer año;
    private Integer grado;

    @Enumerated(EnumType.STRING)
    private EstudianteEntity.NivelEducativo nivel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estadoPago;

    @Column(name = "monto_matricula")
    private Double montoMatricula;

    @Column(name = "monto_mensualidad")
    private Double montoMensualidad;

    @Column(name = "dia_pago")
    private Integer diaPago;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    public enum Estado { ACTIVA, INACTIVA, FINALIZADA }
    public enum EstadoPago { PENDIENTE, PAGADO }
}
