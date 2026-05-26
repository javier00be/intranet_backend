package com.example.intranet_school.infrastructure.config;

import com.example.intranet_school.domain.ports.in.*;
import com.example.intranet_school.domain.ports.out.*;
import com.example.intranet_school.domain.service.*;
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
    public PagoUseCase pagoUseCase(PagoRepositoryPort pagoRepositoryPort,
                                   EventPublisherPort eventPublisherPort) {
        return new PagoServiceImpl(pagoRepositoryPort, eventPublisherPort);
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

    @Bean
    public PadreUseCase padreUseCase(PadreRepositoryPort padreRepositoryPort,
                                     EstudianteRepositoryPort estudianteRepositoryPort) {
        return new PadreServiceImpl(padreRepositoryPort, estudianteRepositoryPort);
    }

    @Bean
    public ChatUseCase chatUseCase(ConversacionRepositoryPort conversacionRepositoryPort,
                                   MensajeRepositoryPort mensajeRepositoryPort) {
        return new ChatServiceImpl(conversacionRepositoryPort, mensajeRepositoryPort);
    }
}
