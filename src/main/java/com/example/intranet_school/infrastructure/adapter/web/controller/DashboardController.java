package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.DashboardDTO;
import com.example.intranet_school.domain.ports.in.DashboardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardUseCase dashboardUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR', 'PROFESOR', 'ESTUDIANTE', 'PADRE')")
    public ResponseEntity<DashboardDTO> getDashboard(Authentication auth) {
        String email = auth.getName();
        String role  = auth.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse("");
        return ResponseEntity.ok(dashboardUseCase.getDashboardData(email, role));
    }
}

