package com.example.intranet_school.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UsuarioEntity usuario;

    @Column(unique = true)
    private String dni;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String direccion;
    private String telefono;
    private Integer grado;
    private String seccion;

    @Enumerated(EnumType.STRING)
    private NivelEducativo nivel;

    public enum NivelEducativo {
        INICIAL, PRIMARIA, SECUNDARIA
    }
}