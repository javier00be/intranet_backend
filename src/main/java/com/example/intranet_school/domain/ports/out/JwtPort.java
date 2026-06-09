package com.example.intranet_school.domain.ports.out;

import com.example.intranet_school.domain.model.Usuario;

public interface JwtPort {
    String generateToken(Usuario usuario);
    Long getUserIdFromToken(String token);
    String getEmailFromToken(String token);
    String getRoleFromToken(String token);
    boolean validateToken(String token);
}