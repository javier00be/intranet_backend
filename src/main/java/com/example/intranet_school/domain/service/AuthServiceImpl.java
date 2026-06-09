package com.example.intranet_school.domain.service;

import com.example.intranet_school.application.dto.*;
import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.domain.ports.in.AuthUseCase;
import com.example.intranet_school.domain.ports.out.JwtPort;
import com.example.intranet_school.domain.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import com.example.intranet_school.domain.ports.out.PasswordEncryptorPort;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthUseCase {
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final JwtPort jwtPort;
    private final PasswordEncryptorPort passwordEncryptorPort;

    @Override
    public AuthResponse login(LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepositoryPort.findByEmail(request.getEmail());

        if (usuarioOpt.isEmpty() || !passwordEncryptorPort.matches(request.getPassword(), usuarioOpt.get().getPassword())) {
            return AuthResponse.builder().success(false).message("Credenciales inválidas").build();
        }

        Usuario usuario = usuarioOpt.get();
        String token = jwtPort.generateToken(usuario);
        UsuarioDTO userDTO = toUsuarioDTO(usuario);

        return AuthResponse.builder().success(true).token(token).user(userDTO).build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepositoryPort.existsByEmail(request.getEmail())) {
            return AuthResponse.builder().success(false).message("El correo ya está registrado").build();
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncryptorPort.encode(request.getPassword()));
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setRol(request.getRol());
        usuario.setAvatar("https://ui-avatars.com/api/?name=" + request.getNombre() + "+" + request.getApellido() + "&background=random");

        usuario = usuarioRepositoryPort.save(usuario);

        String token = jwtPort.generateToken(usuario);
        UsuarioDTO userDTO = toUsuarioDTO(usuario);

        return AuthResponse.builder().success(true).token(token).user(userDTO).build();
    }

    private UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .rol(usuario.getRol())
                .avatar(usuario.getAvatar())
                .build();
    }
}