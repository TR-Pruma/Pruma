package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.repository.ComunicacaoRepository;
import com.br.pruma.core.repository.port.ComunicacaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link ComunicacaoRepositoryPort}
 * delegando ao {@link ComunicacaoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class ComunicacaoRepositoryAdapter implements ComunicacaoRepositoryPort {

    private final ComunicacaoRepository comunicacaoRepository;

    @Override
    public Comunicacao save(Comunicacao comunicacao) {
        return comunicacaoRepository.save(comunicacao);
    }

    @Override
    public Optional<Comunicacao> findById(Integer id) {
        return comunicacaoRepository.findById(id);
    }

    @Override
    public Optional<Comunicacao> findByIdAndAtivoTrue(Integer id) {
        return comunicacaoRepository.findByIdAndAtivoTrue(id);
    }

    @Override
    public Page<Comunicacao> findByProjetoIdAndAtivoTrueOrderByCreatedAtDesc(Integer projetoId, Pageable pageable) {
        return comunicacaoRepository.findByProjetoIdAndAtivoTrueOrderByCreatedAtDesc(projetoId, pageable);
    }

    @Override
    public List<Comunicacao> findByClienteIdAndAtivoTrueOrderByCreatedAtDesc(Integer clienteId) {
        return comunicacaoRepository.findByClienteIdAndAtivoTrueOrderByCreatedAtDesc(clienteId);
    }

    @Override
    public Page<Comunicacao> findByProjetoIdAndClienteIdAndAtivoTrueOrderByCreatedAtDesc(Integer projetoId, Integer clienteId, Pageable pageable) {
        return comunicacaoRepository.findByProjetoIdAndClienteIdAndAtivoTrueOrderByCreatedAtDesc(projetoId, clienteId, pageable);
    }

    @Override
    public List<Comunicacao> findAll() {
        return comunicacaoRepository.findAll();
    }

    @Override
    public Page<Comunicacao> findAll(Pageable pageable) {
        return comunicacaoRepository.findAll(pageable);
    }

    @Override
    public boolean existsByProjetoIdAndClienteIdAndAtivoTrue(Integer projetoId, Integer clienteId) {
        return comunicacaoRepository.existsByProjetoIdAndClienteIdAndAtivoTrue(projetoId, clienteId);
    }

    @Override
    public void deleteById(Integer id) {
        comunicacaoRepository.deleteById(id);
    }

    @Override
    public void delete(Comunicacao comunicacao) {
        comunicacaoRepository.delete(comunicacao);
    }

    @Override
    public boolean existsById(Integer id) {
        return comunicacaoRepository.existsById(id);
    }
}
