package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.StatusSolicitacao;
import com.br.pruma.core.repository.StatusSolicitacaoRepository;
import com.br.pruma.core.repository.port.StatusSolicitacaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link StatusSolicitacaoRepositoryPort}
 * delegando ao {@link StatusSolicitacaoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class StatusSolicitacaoRepositoryAdapter implements StatusSolicitacaoRepositoryPort {

    private final StatusSolicitacaoRepository statusSolicitacaoRepository;

    @Override
    public StatusSolicitacao save(StatusSolicitacao statusSolicitacao) {
        return statusSolicitacaoRepository.save(statusSolicitacao);
    }

    @Override
    public Optional<StatusSolicitacao> findById(Integer id) {
        return statusSolicitacaoRepository.findById(id);
    }

    @Override
    public List<StatusSolicitacao> findAll() {
        return statusSolicitacaoRepository.findAll();
    }

    @Override
    public Page<StatusSolicitacao> findAll(Pageable pageable) {
        return statusSolicitacaoRepository.findAll(pageable);
    }

    @Override
    public void delete(StatusSolicitacao statusSolicitacao) {
        statusSolicitacaoRepository.delete(statusSolicitacao);
    }

    @Override
    public void deleteById(Integer id) {
        statusSolicitacaoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return statusSolicitacaoRepository.existsById(id);
    }
}