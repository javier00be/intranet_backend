package com.example.intranet_school.infrastructure.service;

import com.example.intranet_school.application.dto.MatriculaCreateRequest;
import com.example.intranet_school.domain.model.Matricula;
import com.example.intranet_school.domain.ports.in.MatriculaUseCase;
import com.example.intranet_school.domain.ports.out.*;
import com.example.intranet_school.domain.service.MatriculaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MatriculaTransactionalService implements MatriculaUseCase {

    private final MatriculaRepositoryPort matriculaRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;
    private final PadreRepositoryPort padreRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncryptorPort passwordEncryptorPort;
    private final MensualidadRepositoryPort mensualidadRepositoryPort;

    private MatriculaUseCase delegate() {
        return new MatriculaServiceImpl(
                matriculaRepositoryPort, estudianteRepositoryPort, padreRepositoryPort,
                usuarioRepositoryPort, passwordEncryptorPort, mensualidadRepositoryPort
        );
    }

    @Override
    @Transactional
    public List<Matricula> crearMatriculas(MatriculaCreateRequest request) {
        return delegate().crearMatriculas(request);
    }

    @Override
    @Transactional
    public Matricula pagarMatricula(Long id) {
        return delegate().pagarMatricula(id);
    }

    @Override
    public List<Matricula> getAllMatriculas() {
        return delegate().getAllMatriculas();
    }

    @Override
    public Optional<Matricula> getMatriculaById(Long id) {
        return delegate().getMatriculaById(id);
    }

    @Override
    public Optional<Matricula> getMiMatricula(String email) {
        return delegate().getMiMatricula(email);
    }

    @Override
    public void deleteMatricula(Long id) {
        delegate().deleteMatricula(id);
    }
}
