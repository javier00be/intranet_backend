package com.example.intranet_school.application.dto.chat;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MensajeDTO {
    private Long id;
    private Long conversacionId;
    private Long emisorId;
    private String emisorNombre;
    private String contenido;
    private LocalDateTime enviadoEn;
    private Boolean leido;
}
