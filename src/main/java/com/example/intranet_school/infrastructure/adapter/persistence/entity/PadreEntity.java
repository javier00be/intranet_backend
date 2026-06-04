package com.example.intranet_school.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "padres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PadreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UsuarioEntity usuario;

    @Column(unique = true)
    private String dni;

    private String telefono;
    @Builder.Default
    private boolean activo = true;

    @ManyToMany
    @JoinTable(
        name = "padre_estudiante",
        joinColumns = @JoinColumn(name = "padre_id"),
        inverseJoinColumns = @JoinColumn(name = "estudiante_id")
    )
    @Builder.Default
    private List<EstudianteEntity> hijos = new ArrayList<>();
}
