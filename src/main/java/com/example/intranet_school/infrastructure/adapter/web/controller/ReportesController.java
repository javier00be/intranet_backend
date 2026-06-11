package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.ReportesDTO;
import com.example.intranet_school.domain.ports.in.ReportesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportesController {

    private final ReportesUseCase reportesUseCase;

    @GetMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<ReportesDTO> getReportes() {
        return ResponseEntity.ok(reportesUseCase.getReportes());
    }
}
