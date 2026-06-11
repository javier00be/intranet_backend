package com.example.intranet_school.domain.service;

import com.example.intranet_school.application.dto.ReportesDTO;
import com.example.intranet_school.domain.model.Asistencia;
import com.example.intranet_school.domain.model.Calificacion;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Pago;
import com.example.intranet_school.domain.ports.in.ReportesUseCase;
import com.example.intranet_school.domain.ports.out.AsistenciaRepositoryPort;
import com.example.intranet_school.domain.ports.out.CalificacionRepositoryPort;
import com.example.intranet_school.domain.ports.out.PagoRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReportesServiceImpl implements ReportesUseCase {

    private final CalificacionRepositoryPort calificacionRepositoryPort;
    private final AsistenciaRepositoryPort   asistenciaRepositoryPort;
    private final PagoRepositoryPort         pagoRepositoryPort;

    @Override
    public ReportesDTO getReportes() {
        List<Calificacion> calificaciones = calificacionRepositoryPort.findAll();

        return ReportesDTO.builder()
                .rendimientoGrado(buildRendimientoGrado(calificaciones))
                .pagosPagados(pagoRepositoryPort.countByEstado(Pago.Estado.PAGADO))
                .pagosPendientes(pagoRepositoryPort.countByEstado(Pago.Estado.PENDIENTE))
                .pagosVencidos(pagoRepositoryPort.countByEstado(Pago.Estado.VENCIDO))
                .asistenciaMensual(buildAsistenciaMensual())
                .alertas(buildAlertas(calificaciones))
                .build();
    }

    private List<ReportesDTO.GradoPromedio> buildRendimientoGrado(List<Calificacion> calificaciones) {
        // Sort key: nivelOrdinal * 10 + grado ensures INICIAL < PRIMARIA < SECUNDARIA within each grado
        TreeMap<Integer, double[]> sums   = new TreeMap<>();
        Map<Integer, String>      labels  = new HashMap<>();

        for (Calificacion c : calificaciones) {
            if (c.getEstudiante() == null || c.getValor() == null) continue;
            Estudiante est = c.getEstudiante();
            if (est.getNivel() == null || est.getGrado() == null) continue;

            int key = est.getNivel().ordinal() * 10 + est.getGrado();
            sums.computeIfAbsent(key, k -> new double[]{0.0, 0.0});
            sums.get(key)[0] += c.getValor();
            sums.get(key)[1]++;
            labels.putIfAbsent(key, est.getGrado() + "° " + capitalize(est.getNivel().name()));
        }

        return sums.entrySet().stream()
                .map(e -> {
                    double[] arr = e.getValue();
                    double avg = arr[1] > 0 ? round1(arr[0] / arr[1]) : 0;
                    return ReportesDTO.GradoPromedio.builder()
                            .label(labels.get(e.getKey()))
                            .promedio(avg)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private List<ReportesDTO.AsistenciaMes> buildAsistenciaMensual() {
        List<Asistencia> asistencias = asistenciaRepositoryPort.findAll();
        TreeMap<YearMonth, long[]> byMes = new TreeMap<>();

        for (Asistencia a : asistencias) {
            if (a.getFecha() == null) continue;
            YearMonth ym = YearMonth.from(a.getFecha());
            byMes.computeIfAbsent(ym, k -> new long[]{0, 0});
            byMes.get(ym)[1]++;
            if (Boolean.TRUE.equals(a.getPresente())) byMes.get(ym)[0]++;
        }

        return byMes.entrySet().stream()
                .map(e -> {
                    YearMonth ym  = e.getKey();
                    long[]    arr = e.getValue();
                    double pct = arr[1] > 0 ? round1(arr[0] * 100.0 / arr[1]) : 0;
                    String label = ym.getMonth().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("es"))
                            + " " + ym.getYear();
                    return ReportesDTO.AsistenciaMes.builder()
                            .mes(label)
                            .porcentaje(pct)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private List<ReportesDTO.AlertaEstudiante> buildAlertas(List<Calificacion> calificaciones) {
        Map<Long, List<Calificacion>> byEstudiante = calificaciones.stream()
                .filter(c -> c.getEstudiante() != null && c.getValor() != null)
                .collect(Collectors.groupingBy(c -> c.getEstudiante().getId()));

        List<ReportesDTO.AlertaEstudiante> alertas = new ArrayList<>();

        for (List<Calificacion> cals : byEstudiante.values()) {
            double avg = cals.stream().mapToDouble(Calificacion::getValor).average().orElse(0);
            if (avg >= 11.0) continue;

            Estudiante est = cals.get(0).getEstudiante();
            String nombre = est.getUsuario() != null
                    ? est.getUsuario().getNombre() + " " + est.getUsuario().getApellido()
                    : "Estudiante #" + est.getId();
            String grado = est.getGrado() != null && est.getNivel() != null
                    ? est.getGrado() + "° " + capitalize(est.getNivel().name())
                    : "-";

            alertas.add(ReportesDTO.AlertaEstudiante.builder()
                    .nombre(nombre)
                    .grado(grado)
                    .promedio(round1(avg))
                    .build());
        }

        alertas.sort(Comparator.comparingDouble(ReportesDTO.AlertaEstudiante::getPromedio));
        return alertas;
    }

    private double round1(double v) {
        return Math.round(v * 10.0) / 10.0;
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }
}
