package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Auditoria;
import com.br.pruma.core.repository.AuditoriaRepository;
import com.br.pruma.core.repository.port.AuditoriaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter de saída que implementa {@link AuditoriaRepositoryPort}
 * delegando ao {@link AuditoriaRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class AuditoriaRepositoryAdapter implements AuditoriaRepositoryPort {

    private final AuditoriaRepository auditoriaRepository;

    @Override
    public Auditoria save(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }

    @Override
    public Optional<Auditoria> findById(UUID id) {
        return auditoriaRepository.findById(id);
    }

    @Override
    public List<Auditoria> findAll() {
        return auditoriaRepository.findAll();
    }

    @Override
    public Page<Auditoria> findAll(Pageable pageable) {
        return auditoriaRepository.findAll(pageable);
    }

    @Override
    public void deleteById(UUID id) {
        auditoriaRepository.deleteById(id);
    }

    @Override
    public void delete(Auditoria auditoria) {
        auditoriaRepository.delete(auditoria);
    }

    @Override
    public boolean existsById(UUID id) {
        return auditoriaRepository.existsById(id);
    }
}
