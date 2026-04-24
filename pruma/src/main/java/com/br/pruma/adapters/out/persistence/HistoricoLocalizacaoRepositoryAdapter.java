package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.HistoricoLocalizacao;
import com.br.pruma.core.repository.HistoricoLocalizacaoRepository;
import com.br.pruma.core.repository.port.HistoricoLocalizacaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link HistoricoLocalizacaoRepositoryPort}
 * delegando ao {@link HistoricoLocalizacaoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class HistoricoLocalizacaoRepositoryAdapter implements HistoricoLocalizacaoRepositoryPort {

    private final HistoricoLocalizacaoRepository historicoLocalizacaoRepository;

    @Override
    public HistoricoLocalizacao save(HistoricoLocalizacao historicoLocalizacao) {
        return historicoLocalizacaoRepository.save(historicoLocalizacao);
    }

    @Override
    public Optional<HistoricoLocalizacao> findById(Integer id) {
        return historicoLocalizacaoRepository.findById(id);
    }

    @Override
    public List<HistoricoLocalizacao> findAll() {
        return historicoLocalizacaoRepository.findAll();
    }

    @Override
    public Page<HistoricoLocalizacao> findAll(Pageable pageable) {
        return historicoLocalizacaoRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer id) {
        historicoLocalizacaoRepository.deleteById(id);
    }

    @Override
    public void delete(HistoricoLocalizacao historicoLocalizacao) {
        historicoLocalizacaoRepository.delete(historicoLocalizacao);
    }

    @Override
    public boolean existsById(Integer id) {
        return historicoLocalizacaoRepository.existsById(id);
    }
}
