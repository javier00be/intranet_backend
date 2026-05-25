package com.example.intranet_school.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversacion {
    private Long id;
    private Usuario participante1;
    private Usuario participante2;
    private LocalDateTime creadoEn;
}
