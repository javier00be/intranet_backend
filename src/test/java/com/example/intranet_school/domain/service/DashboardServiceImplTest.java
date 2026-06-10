package com.example.intranet_school.domain.service;

import com.example.intranet_school.application.dto.DashboardDTO;
import com.example.intranet_school.domain.model.Curso;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock EstudianteRepositoryPort estudianteRepositoryPort;
    @Mock ProfesorRepositoryPort  profesorRepositoryPort;
    @Mock CursoRepositoryPort     cursoRepositoryPort;
    @Mock PagoRepositoryPort      pagoRepositoryPort;
    @InjectMocks DashboardServiceImpl dashboardService;

    @Test
    void getDashboardData_director_returnsRealCounts() {
        when(estudianteRepositoryPort.count()).thenReturn(3L);
        when(profesorRepositoryPort.count()).thenReturn(2L);
        when(cursoRepositoryPort.count()).thenReturn(4L);
        when(pagoRepositoryPort.countByEstado(Pago.Estado.PENDIENTE)).thenReturn(1L);

        DashboardDTO result = dashboardService.getDashboardData("director@einstein.edu.pe", "DIRECTOR");

        assertEquals(3L, result.getTotalEstudiantes());
        assertEquals(2L, result.getTotalProfesores());
        assertEquals(4L, result.getTotalCursos());
        assertEquals(1L, result.getPagosPendientes());
    }

    @Test
    void getDashboardData_director_usesCountMethodsOnly() {
        when(estudianteRepositoryPort.count()).thenReturn(0L);
        when(profesorRepositoryPort.count()).thenReturn(0L);
        when(cursoRepositoryPort.count()).thenReturn(0L);
        when(pagoRepositoryPort.countByEstado(any())).thenReturn(0L);

        dashboardService.getDashboardData("director@einstein.edu.pe", "DIRECTOR");

        verify(estudianteRepositoryPort).count();
        verify(profesorRepositoryPort).count();
        verify(cursoRepositoryPort).count();
        verify(pagoRepositoryPort).countByEstado(Pago.Estado.PENDIENTE);
        verifyNoMoreInteractions(estudianteRepositoryPort, profesorRepositoryPort,
                                 cursoRepositoryPort, pagoRepositoryPort);
    }

    @Test
    void getDashboardData_profesor_returnsCursoCount() {
        Profesor profesor = new Profesor();
        profesor.setId(5L);
        when(profesorRepositoryPort.findByUsuarioEmail("c.rodriguez@einstein.edu.pe"))
                .thenReturn(Optional.of(profesor));
        when(cursoRepositoryPort.findByProfesorId(5L)).thenReturn(List.of(new Curso(), new Curso()));

        DashboardDTO result = dashboardService.getDashboardData("c.rodriguez@einstein.edu.pe", "PROFESOR");

        assertEquals(2L, result.getMisCursos());
        verifyNoInteractions(estudianteRepositoryPort, pagoRepositoryPort);
    }

    @Test
    void getDashboardData_profesor_emailNotFound_returnsEmptyDto() {
        when(profesorRepositoryPort.findByUsuarioEmail("inexistente@einstein.edu.pe"))
                .thenReturn(Optional.empty());

        DashboardDTO result = dashboardService.getDashboardData("inexistente@einstein.edu.pe", "PROFESOR");

        assertNull(result.getMisCursos());
        verifyNoInteractions(cursoRepositoryPort);
    }

    @Test
    void getDashboardData_estudiante_returnsZeros() {
        DashboardDTO result = dashboardService.getDashboardData("sofia.perez@einstein.edu.pe", "ESTUDIANTE");

        assertEquals(0L, result.getMisCursos());
        assertEquals(0L, result.getMisTareas());
        assertEquals(0L, result.getMisNotas());
        verifyNoInteractions(estudianteRepositoryPort, profesorRepositoryPort,
                             cursoRepositoryPort, pagoRepositoryPort);
    }

    @Test
    void getDashboardData_unknownRole_returnsEmptyDto() {
        DashboardDTO result = dashboardService.getDashboardData("unknown@einstein.edu.pe", "DESCONOCIDO");

        assertNull(result.getTotalEstudiantes());
        assertNull(result.getMisCursos());
        verifyNoInteractions(estudianteRepositoryPort, profesorRepositoryPort,
                             cursoRepositoryPort, pagoRepositoryPort);
    }
}
