package com.example.intranet_school.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matricula {
    private Long id;
    private Estudiante estudiante;
    private Integer año;
    private Integer grado;
    private NivelEducativo nivel;
    private Estado estado;
    private EstadoPago estadoPago;
    private Double montoMatricula;
    private Double montoMensualidad;
    private Integer diaPago;
    private LocalDateTime fechaCreacion;

    public enum Estado { ACTIVA, INACTIVA, FINALIZADA }
    public enum EstadoPago { PENDIENTE, PAGADO }
    public enum NivelEducativo { INICIAL, PRIMARIA, SECUNDARIA }
}
