package com.example.intranet_school.domain.service;

import com.example.intranet_school.domain.model.Curso;
import com.example.intranet_school.domain.ports.out.CursoRepositoryPort;
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
class CursoServiceImplTest {

    @Mock CursoRepositoryPort cursoRepositoryPort;
    @InjectMocks CursoServiceImpl cursoService;

    @Test
    void getAllCursos_delegatesToPort() {
        List<Curso> cursos = List.of(new Curso(), new Curso());
        when(cursoRepositoryPort.findAll()).thenReturn(cursos);

        List<Curso> result = cursoService.getAllCursos();

        assertEquals(2, result.size());
        verify(cursoRepositoryPort).findAll();
    }

    @Test
    void getCursoById_whenExists_returnsCurso() {
        Curso curso = new Curso();
        curso.setId(1L);
        when(cursoRepositoryPort.findById(1L)).thenReturn(Optional.of(curso));

        Optional<Curso> result = cursoService.getCursoById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void getCursoById_whenNotExists_returnsEmpty() {
        when(cursoRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        Optional<Curso> result = cursoService.getCursoById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void createCurso_savesAndReturnsCurso() {
        Curso curso = new Curso();
        curso.setNombre("Matemáticas");
        when(cursoRepositoryPort.save(curso)).thenReturn(curso);

        Curso result = cursoService.createCurso(curso);

        assertEquals("Matemáticas", result.getNombre());
        verify(cursoRepositoryPort).save(curso);
    }

    @Test
    void updateCurso_whenExists_setIdAndSaves() {
        Curso existing = new Curso();
        existing.setId(1L);
        Curso update = new Curso();
        update.setNombre("Física");

        when(cursoRepositoryPort.findById(1L)).thenReturn(Optional.of(existing));
        when(cursoRepositoryPort.save(update)).thenReturn(update);

        cursoService.updateCurso(1L, update);

        assertEquals(1L, update.getId());
        verify(cursoRepositoryPort).save(update);
    }

    @Test
    void updateCurso_whenNotExists_throwsRuntimeException() {
        when(cursoRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cursoService.updateCurso(99L, new Curso()));
        verify(cursoRepositoryPort, never()).save(any());
    }

    @Test
    void deleteCurso_delegatesToPort() {
        cursoService.deleteCurso(1L);
        verify(cursoRepositoryPort).deleteById(1L);
    }
}
