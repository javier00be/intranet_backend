package com.example.intranet_school.application.dto.chat;

import com.example.intranet_school.application.dto.UsuarioDTO;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversacionDTO {
    private Long id;
    private UsuarioDTO participante1;
    private UsuarioDTO participante2;
    private LocalDateTime creadoEn;
}
