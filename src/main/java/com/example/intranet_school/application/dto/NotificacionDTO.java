package com.example.intranet_school.application.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
    private String tipo;
    private String mensaje;
    private LocalDateTime timestamp;
    private Map<String, Object> datos;
}
