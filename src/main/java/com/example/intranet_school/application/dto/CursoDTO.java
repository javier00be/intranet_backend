package com.example.intranet_school.application.dto;

import com.example.intranet_school.domain.model.Estudiante.NivelEducativo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private NivelEducativo nivel;
    @Builder.Default
    private List<Integer> grados = new ArrayList<>();
    private String seccion;
    private Long profesorId;
    private String profesorNombre;
    @JsonProperty("anio")
    private Integer año;
    private boolean activo = true;
}