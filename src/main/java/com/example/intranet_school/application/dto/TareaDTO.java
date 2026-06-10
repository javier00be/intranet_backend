package com.example.intranet_school.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TareaDTO {
    private Long id;
    private Long cursoId;
    private String cursoNombre;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaEntrega;
    private String archivoUrl;
}
