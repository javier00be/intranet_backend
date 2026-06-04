package com.example.intranet_school.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensualidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensualidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id", nullable = false)
    private MatriculaEntity matricula;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mes mes;

    @Column(nullable = false)
    private Integer año;

    @Column(nullable = false)
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estadoPago;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    public enum Mes {
        ENERO, FEBRERO, MARZO, ABRIL, MAYO, JUNIO,
        JULIO, AGOSTO, SEPTIEMBRE, OCTUBRE, NOVIEMBRE, DICIEMBRE
    }

    public enum EstadoPago { PENDIENTE, PAGADO }
}
