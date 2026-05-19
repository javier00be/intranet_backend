package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.DashboardDTO;
import com.example.intranet_school.domain.ports.in.DashboardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {
    private final DashboardUseCase dashboardUseCase;

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboard(
            @RequestParam String role,
            @RequestParam Long userId) {
        return ResponseEntity.ok(dashboardUseCase.getDashboardData(role, userId));
    }
}
