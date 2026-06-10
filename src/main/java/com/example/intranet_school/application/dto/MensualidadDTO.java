package com.example.intranet_school.application.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MensualidadDTO {
    private Long id;
    private Long matriculaId;
    private Long estudianteId;
    private String estudianteNombre;
    private Integer grado;
    private String nivel;
    private String mes;
    private Integer anio;
    private Double monto;
    private String estadoPago;
    private LocalDate fechaVencimiento;
    private LocalDateTime fechaPago;
    private String comprobanteUrl;
    private String nroTransaccion;
}
