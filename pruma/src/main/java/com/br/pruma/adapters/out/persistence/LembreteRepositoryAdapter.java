package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Lembrete;
import com.br.pruma.core.repository.LembreteRepository;
import com.br.pruma.core.repository.port.LembreteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link LembreteRepositoryPort}
 * delegando ao {@link LembreteRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class LembreteRepositoryAdapter implements LembreteRepositoryPort {

    private final LembreteRepository lembreteRepository;

    @Override
    public Lembrete save(Lembrete lembrete) {
        return lembreteRepository.save(lembrete);
    }

    @Override
    public Optional<Lembrete> findById(Integer id) {
        return lembreteRepository.findById(id);
    }

    @Override
    public List<Lembrete> findAll() {
        return lembreteRepository.findAll();
    }

    @Override
    public Page<Lembrete> findAll(Pageable pageable) {
        return lembreteRepository.findAll(pageable);
    }

    @Override
    public List<Lembrete> findByCliente_CpfOrderByDataHora(String clienteCpf) {
        return lembreteRepository.findByCliente_CpfOrderByDataHora(clienteCpf);
    }

    @Override
    public List<Lembrete> findByTipoUsuario_IdOrderByDataHora(Integer tipoUsuarioId) {
        return lembreteRepository.findByTipoUsuario_IdOrderByDataHora(tipoUsuarioId);
    }

    @Override
    public void deleteById(Integer id) {
        lembreteRepository.deleteById(id);
    }

    @Override
    public void delete(Lembrete lembrete) {
        lembreteRepository.delete(lembrete);
    }

    @Override
    public boolean existsById(Integer id) {
        return lembreteRepository.existsById(id);
    }
}
