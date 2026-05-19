package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Pago;
import com.example.intranet_school.domain.ports.out.PagoRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.entity.PagoEntity;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.PagoMapper;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.PagoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PagoRepositoryAdapter implements PagoRepositoryPort {
    private final PagoJpaRepository pagoJpaRepository;
    private final PagoMapper pagoMapper;

    @Override
    public List<Pago> findAll() {
        return pagoJpaRepository.findAll().stream()
                .map(pagoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pago> findByEstudianteId(Long estudianteId) {
        return pagoJpaRepository.findByEstudianteId(estudianteId).stream()
                .map(pagoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pago> findByEstado(Pago.Estado estado) {
        return pagoJpaRepository.findByEstado(PagoEntity.Estado.valueOf(estado.name())).stream()
                .map(pagoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Pago save(Pago pago) {
        return pagoMapper.toDomain(pagoJpaRepository.save(pagoMapper.toEntity(pago)));
    }

    @Override
    public void deleteById(Long id) {
        pagoJpaRepository.deleteById(id);
    }
}
