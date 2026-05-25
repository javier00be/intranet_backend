package com.example.intranet_school.infrastructure.scheduler;

import com.example.intranet_school.domain.ports.out.MensajeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ChatCleanupJob {
    private final MensajeRepositoryPort mensajeRepositoryPort;

    @Scheduled(cron = "0 0 2 * * *")
    public void limpiarMensajesAntiguos() {
        LocalDateTime limite = LocalDateTime.now().minusDays(3);
        mensajeRepositoryPort.deleteByEnviadoEnBefore(limite);
    }
}
