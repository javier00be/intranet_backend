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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "curso_profesores",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "profesor_id")
    )
    @Builder.Default
    private List<ProfesorEntity> profesores = new ArrayList<>();
    @Column(name = "anio")
    private Integer año;
}