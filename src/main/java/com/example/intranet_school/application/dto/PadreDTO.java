package com.example.intranet_school.application.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PadreDTO {
    private Long id;
    private UsuarioDTO usuario;
    private String telefono;
    private List<Long> hijoIds;
}
