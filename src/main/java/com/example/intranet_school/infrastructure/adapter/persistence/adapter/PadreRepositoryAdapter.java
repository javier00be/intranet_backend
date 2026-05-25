package com.example.intranet_school.infrastructure.adapter.persistence.adapter;

import com.example.intranet_school.domain.model.Padre;
import com.example.intranet_school.domain.ports.out.PadreRepositoryPort;
import com.example.intranet_school.infrastructure.adapter.persistence.mapper.PadreMapper;
import com.example.intranet_school.infrastructure.adapter.persistence.repository.PadreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PadreRepositoryAdapter implements PadreRepositoryPort {
    private final PadreJpaRepository padreJpaRepository;
    private final PadreMapper padreMapper;

    @Override
    public List<Padre> findAll() {
        return padreJpaRepository.findAll().stream()
                .map(padreMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Padre> findById(Long id) {
        return padreJpaRepository.findById(id).map(padreMapper::toDomain);
    }

    @Override
    public Optional<Padre> findByUsuarioId(Long usuarioId) {
        return padreJpaRepository.findByUsuarioId(usuarioId).map(padreMapper::toDomain);
    }

    @Override
    public Padre save(Padre padre) {
        return padreMapper.toDomain(padreJpaRepository.save(padreMapper.toEntity(padre)));
    }

    @Override
    public void deleteById(Long id) {
        padreJpaRepository.deleteById(id);
    }
}
