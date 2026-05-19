package com.example.intranet_school.domain.service;

import com.example.intranet_school.application.dto.DashboardDTO;
import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.model.Estudiante;
import com.example.intranet_school.domain.model.Pago;
import com.example.intranet_school.domain.model.Profesor;
import com.example.intranet_school.domain.ports.out.CursoRepositoryPort;
import com.example.intranet_school.domain.ports.out.EstudianteRepositoryPort;
import com.example.intranet_school.domain.ports.out.PagoRepositoryPort;
import com.example.intranet_school.domain.ports.out.ProfesorRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock EstudianteRepositoryPort estudianteRepositoryPort;
    @Mock ProfesorRepositoryPort profesorRepositoryPort;
    @Mock CursoRepositoryPort cursoRepositoryPort;
    @Mock PagoRepositoryPort pagoRepositoryPort;
    @InjectMocks DashboardServiceImpl dashboardService;

    @Test
    void getDashboardData_director_returnsRealCounts() {
        when(estudianteRepositoryPort.findAll()).thenReturn(List.of(new Estudiante(), new Estudiante(), new Estudiante()));
        when(profesorRepositoryPort.findAll()).thenReturn(List.of(new Profesor(), new Profesor()));
        when(cursoRepositoryPort.findAll()).thenReturn(List.of(new Curso(), new Curso(), new Curso(), new Curso()));
        when(pagoRepositoryPort.findByEstado(Pago.Estado.PENDIENTE)).thenReturn(List.of(new Pago()));

        DashboardDTO result = dashboardService.getDashboardData("DIRECTOR", 1L);

        assertEquals(3L, result.getTotalEstudiantes());
        assertEquals(2L, result.getTotalProfesores());
        assertEquals(4L, result.getTotalCursos());
        assertEquals(1L, result.getPagosPendientes());
    }

    @Test
    void getDashboardData_director_noInteractionWithOtherRepos() {
        when(estudianteRepositoryPort.findAll()).thenReturn(List.of());
        when(profesorRepositoryPort.findAll()).thenReturn(List.of());
        when(cursoRepositoryPort.findAll()).thenReturn(List.of());
        when(pagoRepositoryPort.findByEstado(any())).thenReturn(List.of());

        dashboardService.getDashboardData("DIRECTOR", 1L);

        verify(estudianteRepositoryPort).findAll();
        verify(profesorRepositoryPort).findAll();
        verify(cursoRepositoryPort).findAll();
        verify(pagoRepositoryPort).findByEstado(Pago.Estado.PENDIENTE);
    }

    @Test
    void getDashboardData_profesor_returnsCursoCount() {
        Curso c1 = new Curso();
        Curso c2 = new Curso();
        when(cursoRepositoryPort.findByProfesorId(5L)).thenReturn(List.of(c1, c2));

        DashboardDTO result = dashboardService.getDashboardData("PROFESOR", 5L);

        assertEquals(2L, result.getMisCursos());
        verifyNoInteractions(estudianteRepositoryPort, profesorRepositoryPort, pagoRepositoryPort);
    }

    @Test
    void getDashboardData_estudiante_returnsZeros() {
        DashboardDTO result = dashboardService.getDashboardData("ESTUDIANTE", 1L);

        assertEquals(0L, result.getMisCursos());
        assertEquals(0L, result.getMisTareas());
        assertEquals(0L, result.getMisNotas());
        verifyNoInteractions(estudianteRepositoryPort, profesorRepositoryPort, cursoRepositoryPort, pagoRepositoryPort);
    }

    @Test
    void getDashboardData_unknownRole_returnsEmptyDto() {
        DashboardDTO result = dashboardService.getDashboardData("DESCONOCIDO", 1L);

        assertNull(result.getTotalEstudiantes());
        assertNull(result.getMisCursos());
        verifyNoInteractions(estudianteRepositoryPort, profesorRepositoryPort, cursoRepositoryPort, pagoRepositoryPort);
    }
}
