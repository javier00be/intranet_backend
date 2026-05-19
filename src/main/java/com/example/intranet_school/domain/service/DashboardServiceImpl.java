package com.example.intranet_school.domain.service;

import com.example.intranet_school.application.dto.DashboardDTO;
import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Pago;
import com.example.intranet_school.domain.ports.in.DashboardUseCase;
import com.example.intranet_school.domain.ports.out.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardUseCase {
    private final EstudianteRepositoryPort estudianteRepositoryPort;
    private final ProfesorRepositoryPort profesorRepositoryPort;
    private final CursoRepositoryPort cursoRepositoryPort;
    private final PagoRepositoryPort pagoRepositoryPort;

    @Override
    public DashboardDTO getDashboardData(String role, Long userId) {
        switch (role) {
            case "DIRECTOR": return buildDirectorDashboard();
            case "PROFESOR":  return buildProfesorDashboard(userId);
            case "ESTUDIANTE": return buildEstudianteDashboard();
            default: return DashboardDTO.builder().build();
        }
    }

    private DashboardDTO buildDirectorDashboard() {
        return DashboardDTO.builder()
                .totalEstudiantes((long) estudianteRepositoryPort.findAll().size())
                .totalProfesores((long) profesorRepositoryPort.findAll().size())
                .totalCursos((long) cursoRepositoryPort.findAll().size())
                .pagosPendientes((long) pagoRepositoryPort.findByEstado(Pago.Estado.PENDIENTE).size())
                .build();
    }

    private DashboardDTO buildProfesorDashboard(Long profesorId) {
        List<Curso> cursos = cursoRepositoryPort.findByProfesorId(profesorId);
        return DashboardDTO.builder()
                .misCursos((long) cursos.size())
                .misTareas(0L)
                .misAlumnos(0L)
                .build();
    }

    private DashboardDTO buildEstudianteDashboard() {
        return DashboardDTO.builder()
                .misCursos(0L)
                .misTareas(0L)
                .misNotas(0L)
                .build();
    }
}
