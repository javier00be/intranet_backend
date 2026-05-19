package com.example.intranet_school.application.dto;

import com.example.intranet_school.domain.model.Pago.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Long id;
    private Long estudianteId;
    private Double monto;
    private String concepto;
    private LocalDateTime fechaPago;
    private Estado estado;
    private String metodoPago;
}
