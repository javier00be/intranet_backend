package com.example.intranet_school.domain.ports.in;

import com.example.intranet_school.application.dto.DashboardDTO;

public interface DashboardUseCase {
    DashboardDTO getDashboardData(String role, Long userId);
}
