package com.example.intranet_school.infrastructure.config;

import com.example.intranet_school.domain.ports.in.*;
import com.example.intranet_school.domain.ports.out.*;
import com.example.intranet_school.domain.service.*;
import com.example.intranet_school.domain.service.EventoCalendarioServiceImpl;
import com.example.intranet_school.domain.service.ReportesServiceImpl;
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
    public CursoUseCase cursoUseCase(CursoRepositoryPort cursoRepositoryPort,
                                     ProfesorRepositoryPort profesorRepositoryPort) {
        return new CursoServiceImpl(cursoRepositoryPort, profesorRepositoryPort);
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
                                             PagoRepositoryPort pagoRepositoryPort,
                                             TareaRepositoryPort tareaRepositoryPort,
                                             CalificacionRepositoryPort calificacionRepositoryPort) {
        return new DashboardServiceImpl(estudianteRepositoryPort, profesorRepositoryPort,
                cursoRepositoryPort, pagoRepositoryPort, tareaRepositoryPort, calificacionRepositoryPort);
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

    @Bean
    public MensualidadUseCase mensualidadUseCase(MensualidadRepositoryPort mensualidadRepositoryPort) {
        return new MensualidadServiceImpl(mensualidadRepositoryPort);
    }

    @Bean
    public EstudianteUseCase estudianteUseCase(EstudianteRepositoryPort estudianteRepositoryPort) {
        return new EstudianteServiceImpl(estudianteRepositoryPort);
    }

    @Bean
    public CalificacionUseCase calificacionUseCase(CalificacionRepositoryPort calificacionRepositoryPort) {
        return new CalificacionServiceImpl(calificacionRepositoryPort);
    }

    @Bean
    public AsistenciaUseCase asistenciaUseCase(AsistenciaRepositoryPort asistenciaRepositoryPort) {
        return new AsistenciaServiceImpl(asistenciaRepositoryPort);
    }

    @Bean
    public TareaUseCase tareaUseCase(TareaRepositoryPort tareaRepositoryPort) {
        return new TareaServiceImpl(tareaRepositoryPort);
    }

    @Bean
    public EventoCalendarioUseCase eventoCalendarioUseCase(EventoCalendarioRepositoryPort eventoCalendarioRepositoryPort) {
        return new EventoCalendarioServiceImpl(eventoCalendarioRepositoryPort);
    }

    @Bean
    public ReportesUseCase reportesUseCase(CalificacionRepositoryPort calificacionRepositoryPort,
                                           AsistenciaRepositoryPort asistenciaRepositoryPort,
                                           PagoRepositoryPort pagoRepositoryPort) {
        return new ReportesServiceImpl(calificacionRepositoryPort, asistenciaRepositoryPort, pagoRepositoryPort);
    }
}
