package com.example.intranet_school.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {
    private Long id;
    private Estudiante estudiante;
    private Double monto;
    private String concepto;
    private LocalDateTime fechaPago;
    private Estado estado;
    private String metodoPago;

    public enum Estado {
        PENDIENTE, PAGADO, VENCIDO
    }
}
