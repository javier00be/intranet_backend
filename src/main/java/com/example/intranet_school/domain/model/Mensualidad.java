package com.example.intranet_school.domain.model;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mensualidad {
    private Long id;
    private Matricula matricula;
    private Mes mes;
    private Integer año;
    private Double monto;
    private EstadoPago estadoPago;
    private LocalDate fechaVencimiento;
    private LocalDateTime fechaPago;

    public enum Mes {
        ENERO, FEBRERO, MARZO, ABRIL, MAYO, JUNIO,
        JULIO, AGOSTO, SEPTIEMBRE, OCTUBRE, NOVIEMBRE, DICIEMBRE;

        public int numero() {
            return this.ordinal() + 1;
        }
    }

    public enum EstadoPago { PENDIENTE, PAGADO }
}
