package com.example.intranet_school.infrastructure.config;

import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.domain.ports.out.UsuarioRepositoryPort;
import com.example.intranet_school.domain.ports.out.PasswordEncryptorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncryptorPort passwordEncoder;

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByEmail("director@escuela.com")) {
            crearUsuario("director@escuela.com", "Director", "Administrativo", "DIRECTOR", "director123");
            crearUsuario("profesor@escuela.com", "Juan", "Pérez", "PROFESOR", "profesor123");
            crearUsuario("padre@escuela.com", "Carlos", "López", "PADRE", "padre123");
            crearUsuario("estudiante@escuela.com", "María", "García", "ESTUDIANTE", "estudiante123");
            System.out.println("=== USUARIOS DE PRUEBA CREADOS ===");
        }
    }

    private void crearUsuario(String email, String nombre, String apellido, String rol, String password) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(Usuario.Rol.valueOf(rol));
        usuario.setAvatar("https://ui-avatars.com/api/?name=" + nombre + "+" + apellido + "&background=random");
        usuarioRepository.save(usuario);
    }
}