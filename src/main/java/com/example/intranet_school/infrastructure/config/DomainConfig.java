package com.example.intranet_school.infrastructure.config;

import com.example.intranet_school.domain.ports.in.AuthUseCase;
import com.example.intranet_school.domain.ports.in.CursoUseCase;
import com.example.intranet_school.domain.ports.in.DashboardUseCase;
import com.example.intranet_school.domain.ports.in.PagoUseCase;
import com.example.intranet_school.domain.ports.in.ProfesorUseCase;
import com.example.intranet_school.domain.ports.out.*;
import com.example.intranet_school.domain.service.AuthServiceImpl;
import com.example.intranet_school.domain.service.CursoServiceImpl;
import com.example.intranet_school.domain.service.DashboardServiceImpl;
import com.example.intranet_school.domain.service.PagoServiceImpl;
import com.example.intranet_school.domain.service.ProfesorServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public AuthUseCase authUseCase(UsuarioRepositoryPort usuarioRepositoryPort,
                                   JwtPort jwtPort,
                                   PasswordEncryptorPort passwordEncryptorPort) {
        return new AuthServiceImpl(usuarioRepositoryPort, jwtPort, passwordEncryptorPort);
    }

    @Bean
    public CursoUseCase cursoUseCase(CursoRepositoryPort cursoRepositoryPort) {
        return new CursoServiceImpl(cursoRepositoryPort);
    }

    @Bean
    public PagoUseCase pagoUseCase(PagoRepositoryPort pagoRepositoryPort) {
        return new PagoServiceImpl(pagoRepositoryPort);
    }

    @Bean
    public ProfesorUseCase profesorUseCase(ProfesorRepositoryPort profesorRepositoryPort) {
        return new ProfesorServiceImpl(profesorRepositoryPort);
    }

    @Bean
    public DashboardUseCase dashboardUseCase(EstudianteRepositoryPort estudianteRepositoryPort,
                                             ProfesorRepositoryPort profesorRepositoryPort,
                                             CursoRepositoryPort cursoRepositoryPort,
                                             PagoRepositoryPort pagoRepositoryPort) {
        return new DashboardServiceImpl(estudianteRepositoryPort, profesorRepositoryPort,
                cursoRepositoryPort, pagoRepositoryPort);
    }
}
