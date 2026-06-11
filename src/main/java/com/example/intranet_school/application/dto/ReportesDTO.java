package com.example.intranet_school.application.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ReportesDTO {
    private List<GradoPromedio> rendimientoGrado;
    private long pagosPagados;
    private long pagosPendientes;
    private long pagosVencidos;
    private List<AsistenciaMes> asistenciaMensual;
    private List<AlertaEstudiante> alertas;

    @Data
    @Builder
    public static class GradoPromedio {
        private String label;
        private double promedio;
    }

    @Data
    @Builder
    public static class AsistenciaMes {
        private String mes;
        private double porcentaje;
    }

    @Data
    @Builder
    public static class AlertaEstudiante {
        private String nombre;
        private String grado;
        private double promedio;
    }
}
