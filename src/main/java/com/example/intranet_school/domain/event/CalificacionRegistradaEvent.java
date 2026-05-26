package com.example.intranet_school.domain.event;

public record CalificacionRegistradaEvent(Long estudianteId, Long cursoId, Double valor, String tipo) {}
