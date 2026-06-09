package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.application.dto.MatriculaCreateRequest;
import com.example.intranet_school.domain.model.Matricula;

import java.util.List;
import java.util.Optional;

public interface MatriculaUseCase {
    List<Matricula> getAllMatriculas();
    Optional<Matricula> getMatriculaById(Long id);
    Optional<Matricula> getMiMatricula(String email);
    List<Matricula> crearMatriculas(MatriculaCreateRequest request);
    Matricula pagarMatricula(Long id);
    void deleteMatricula(Long id);
}
