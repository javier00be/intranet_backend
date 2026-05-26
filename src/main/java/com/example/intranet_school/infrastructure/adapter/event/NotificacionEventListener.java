package com.example.intranet_school.infrastructure.adapter.event;

import com.example.intranet_school.application.dto.NotificacionDTO;
import com.example.intranet_school.domain.event.CalificacionRegistradaEvent;
import com.example.intranet_school.domain.event.PagoRegistradoEvent;
import com.example.intranet_school.domain.event.TareaPublicadaEvent;
import com.example.intranet_school.domain.model.Padre;
import com.example.intranet_school.domain.ports.out.EstudianteRepositoryPort;
import com.example.intranet_school.domain.ports.out.PadreRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificacionEventListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final PadreRepositoryPort padreRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;

    @Async("notificacionExecutor")
    @EventListener
    public void onPagoRegistrado(PagoRegistradoEvent event) {
        List<Padre> padres = padreRepositoryPort.findByHijoId(event.estudianteId());
        NotificacionDTO notificacion = NotificacionDTO.builder()
                .tipo("PAGO")
                .mensaje("Pago registrado: " + event.concepto() + " por S/ " + event.monto())
                .timestamp(LocalDateTime.now())
                .datos(Map.of(
                        "estudianteId", event.estudianteId(),
                        "monto", event.monto(),
                        "concepto", event.concepto()
                ))
                .build();
        padres.forEach(padre -> enviar(padre.getUsuario().getId(), notificacion));
    }

    @Async("notificacionExecutor")
    @EventListener
    public void onCalificacionRegistrada(CalificacionRegistradaEvent event) {
        List<Padre> padres = padreRepositoryPort.findByHijoId(event.estudianteId());
        NotificacionDTO notificacion = NotificacionDTO.builder()
                .tipo("CALIFICACION")
                .mensaje("Nueva calificación registrada: " + event.tipo() + " — " + event.valor())
                .timestamp(LocalDateTime.now())
                .datos(Map.of(
                        "estudianteId", event.estudianteId(),
                        "cursoId", event.cursoId(),
                        "valor", event.valor(),
                        "tipo", event.tipo()
                ))
                .build();

        // Notificar al padre
        padres.forEach(padre -> enviar(padre.getUsuario().getId(), notificacion));

        // Notificar al alumno directamente
        estudianteRepositoryPort.findById(event.estudianteId())
                .ifPresent(e -> enviar(e.getUsuario().getId(), notificacion));
    }

    @Async("notificacionExecutor")
    @EventListener
    public void onTareaPublicada(TareaPublicadaEvent event) {
        // Notificar a todos los alumnos del curso
        estudianteRepositoryPort.findAll().stream()
                .filter(e -> e.getGrado() != null)
                .forEach(e -> {
                    NotificacionDTO notificacion = NotificacionDTO.builder()
                            .tipo("TAREA")
                            .mensaje("Nueva tarea publicada: " + event.titulo())
                            .timestamp(LocalDateTime.now())
                            .datos(Map.of("cursoId", event.cursoId(), "titulo", event.titulo()))
                            .build();
                    enviar(e.getUsuario().getId(), notificacion);
                });
    }

    private void enviar(Long usuarioId, NotificacionDTO notificacion) {
        messagingTemplate.convertAndSendToUser(
                usuarioId.toString(), "/queue/notificaciones", notificacion);
    }
}
