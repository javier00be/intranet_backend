package com.example.intranet_school.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    @Enumerated(EnumType.STRING)
    private EstudianteEntity.NivelEducativo nivel;
    @Convert(converter = IntegerListConverter.class)
    @Column(name = "grados")
    @Builder.Default
    private List<Integer> grados = new ArrayList<>();
    private String seccion;
    @Builder.Default
    private boolean activo = true;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private ProfesorEntity profesor;
    private Integer año;
}