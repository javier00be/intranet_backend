package com.example.intranet_school.domain.service;

import com.example.intranet_school.application.dto.DashboardDTO;
import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Pago;
import com.example.intranet_school.domain.ports.in.DashboardUseCase;
import com.example.intranet_school.domain.ports.out.*;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardUseCase {
    private final EstudianteRepositoryPort estudianteRepositoryPort;
    private final ProfesorRepositoryPort profesorRepositoryPort;
    private final CursoRepositoryPort cursoRepositoryPort;
    private final PagoRepositoryPort pagoRepositoryPort;
    private final TareaRepositoryPort tareaRepositoryPort;
    private final CalificacionRepositoryPort calificacionRepositoryPort;

    @Override
    public DashboardDTO getDashboardData(String email, String role) {
        return switch (role) {
            case "DIRECTOR"   -> buildDirectorDashboard();
            case "PROFESOR"   -> profesorRepositoryPort.findByUsuarioEmail(email)
                                    .map(p -> buildProfesorDashboard(p.getId()))
                                    .orElse(DashboardDTO.builder().build());
            case "ESTUDIANTE" -> buildEstudianteDashboard(email);
            default           -> DashboardDTO.builder().build();
        };
    }

    private DashboardDTO buildDirectorDashboard() {
        return DashboardDTO.builder()
                .totalEstudiantes(estudianteRepositoryPort.count())
                .totalProfesores(profesorRepositoryPort.count())
                .totalCursos(cursoRepositoryPort.count())
                .pagosPendientes(pagoRepositoryPort.countByEstado(Pago.Estado.PENDIENTE))
                .build();
    }

    private DashboardDTO buildProfesorDashboard(Long profesorId) {
        List<Curso> cursos = cursoRepositoryPort.findByProfesorId(profesorId);

        long tareas = cursos.stream()
                .mapToLong(c -> tareaRepositoryPort.findByCursoId(c.getId()).size())
                .sum();

        Set<Long> alumnosUnicos = new HashSet<>();
        cursos.forEach(curso -> {
            if (curso.getNivel() != null) {
                curso.getGrados().forEach(grado ->
                        estudianteRepositoryPort.findByNivelAndGrado(curso.getNivel(), grado)
                                .forEach(e -> alumnosUnicos.add(e.getId()))
                );
            }
        });

        return DashboardDTO.builder()
                .misCursos((long) cursos.size())
                .misTareas(tareas)
                .misAlumnos((long) alumnosUnicos.size())
                .build();
    }

    private DashboardDTO buildEstudianteDashboard(String email) {
        return estudianteRepositoryPort.findByUsuarioEmail(email)
                .map(estudiante -> {
                    List<Curso> cursos = (estudiante.getNivel() != null && estudiante.getGrado() != null)
                            ? cursoRepositoryPort.findByNivelAndGrado(estudiante.getNivel(), estudiante.getGrado())
                            : List.of();

                    long tareas = cursos.stream()
                            .mapToLong(c -> tareaRepositoryPort.findByCursoId(c.getId()).size())
                            .sum();

                    long notas = calificacionRepositoryPort.findByEstudianteId(estudiante.getId()).size();

                    return DashboardDTO.builder()
                            .misCursos((long) cursos.size())
                            .misTareas(tareas)
                            .misNotas(notas)
                            .build();
                })
                .orElse(DashboardDTO.builder().misCursos(0L).misTareas(0L).misNotas(0L).build());
    }
}
