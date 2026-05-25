package com.example.intranet_school.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mensaje {
    private Long id;
    private Long conversacionId;
    private Usuario emisor;
    private String contenido;
    private LocalDateTime enviadoEn;
    private Boolean leido;
}
