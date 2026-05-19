package com.example.intranet_school.domain.service;

import com.example.intranet_school.application.dto.AuthResponse;
import com.example.intranet_school.application.dto.LoginRequest;
import com.example.intranet_school.application.dto.RegisterRequest;
import com.example.intranet_school.domain.model.Usuario;
import com.example.intranet_school.domain.ports.out.JwtPort;
import com.example.intranet_school.domain.ports.out.PasswordEncryptorPort;
import com.example.intranet_school.domain.ports.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock UsuarioRepositoryPort usuarioRepositoryPort;
    @Mock JwtPort jwtPort;
    @Mock PasswordEncryptorPort passwordEncryptorPort;
    @InjectMocks AuthServiceImpl authService;

    // --- login ---

    @Test
    void login_whenUserNotFound_returnsFailure() {
        LoginRequest request = new LoginRequest();
        request.setEmail("noexiste@test.com");
        request.setPassword("cualquier");

        when(usuarioRepositoryPort.findByEmail("noexiste@test.com")).thenReturn(Optional.empty());

        AuthResponse result = authService.login(request);

        assertFalse(result.isSuccess());
        assertEquals("Credenciales inválidas", result.getMessage());
        verifyNoInteractions(jwtPort);
    }

    @Test
    void login_whenPasswordMismatch_returnsFailure() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@test.com");
        request.setPassword("incorrecta");

        Usuario usuario = new Usuario();
        usuario.setPassword("hashed");

        when(usuarioRepositoryPort.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncryptorPort.matches("incorrecta", "hashed")).thenReturn(false);

        AuthResponse result = authService.login(request);

        assertFalse(result.isSuccess());
        assertEquals("Credenciales inválidas", result.getMessage());
        verifyNoInteractions(jwtPort);
    }

    @Test
    void login_whenValidCredentials_returnsTokenAndUser() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@test.com");
        request.setPassword("pass");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@test.com");
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setPassword("hashed");
        usuario.setRol(Usuario.Rol.ESTUDIANTE);

        when(usuarioRepositoryPort.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncryptorPort.matches("pass", "hashed")).thenReturn(true);
        when(jwtPort.generateToken(usuario)).thenReturn("jwt-token");

        AuthResponse result = authService.login(request);

        assertTrue(result.isSuccess());
        assertEquals("jwt-token", result.getToken());
        assertNotNull(result.getUser());
        assertEquals("test@test.com", result.getUser().getEmail());
        assertEquals("Juan", result.getUser().getNombre());
    }

    // --- register ---

    @Test
    void register_whenEmailAlreadyExists_returnsFailure() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("existe@test.com");

        when(usuarioRepositoryPort.existsByEmail("existe@test.com")).thenReturn(true);

        AuthResponse result = authService.register(request);

        assertFalse(result.isSuccess());
        assertEquals("El correo ya está registrado", result.getMessage());
        verify(usuarioRepositoryPort, never()).save(any());
    }

    @Test
    void register_whenNewEmail_savesUserAndReturnsToken() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("nuevo@test.com");
        request.setPassword("pass");
        request.setNombre("Ana");
        request.setApellido("García");
        request.setRol(Usuario.Rol.ESTUDIANTE);

        when(usuarioRepositoryPort.existsByEmail("nuevo@test.com")).thenReturn(false);
        when(passwordEncryptorPort.encode("pass")).thenReturn("hashed");

        Usuario saved = new Usuario();
        saved.setId(2L);
        saved.setEmail("nuevo@test.com");
        saved.setNombre("Ana");
        saved.setApellido("García");
        saved.setRol(Usuario.Rol.ESTUDIANTE);

        when(usuarioRepositoryPort.save(any(Usuario.class))).thenReturn(saved);
        when(jwtPort.generateToken(saved)).thenReturn("nuevo-token");

        AuthResponse result = authService.register(request);

        assertTrue(result.isSuccess());
        assertEquals("nuevo-token", result.getToken());
        assertNotNull(result.getUser());
        assertEquals("nuevo@test.com", result.getUser().getEmail());
        verify(passwordEncryptorPort).encode("pass");
        verify(usuarioRepositoryPort).save(any(Usuario.class));
    }
}
