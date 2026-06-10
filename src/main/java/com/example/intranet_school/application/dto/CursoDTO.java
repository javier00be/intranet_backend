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
    @Builder.Default
    private List<Long> profesorIds = new ArrayList<>();
    @Builder.Default
    private List<String> profesorNombres = new ArrayList<>();
    @JsonProperty("anio")
    private Integer año;
    private Boolean activo;
}