package com.example.intranet_school.domain.event;

public record PagoRegistradoEvent(Long estudianteId, Double monto, String concepto) {}
