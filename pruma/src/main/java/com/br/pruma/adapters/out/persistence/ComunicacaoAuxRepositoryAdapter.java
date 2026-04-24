package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.ComunicacaoAux;
import com.br.pruma.core.repository.ComunicacaoAuxRepository;
import com.br.pruma.core.repository.port.ComunicacaoAuxRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link ComunicacaoAuxRepositoryPort}
 * delegando ao {@link ComunicacaoAuxRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class ComunicacaoAuxRepositoryAdapter implements ComunicacaoAuxRepositoryPort {

    private final ComunicacaoAuxRepository comunicacaoAuxRepository;

    @Override
    public ComunicacaoAux save(ComunicacaoAux comunicacaoAux) {
        return comunicacaoAuxRepository.save(comunicacaoAux);
    }

    @Override
    public Optional<ComunicacaoAux> findById(Integer id) {
        return comunicacaoAuxRepository.findById(id);
    }

    @Override
    public Optional<ComunicacaoAux> findByComunicacaoIdAndAtivoTrue(Integer comunicacaoId) {
        return comunicacaoAuxRepository.findByComunicacaoIdAndAtivoTrue(comunicacaoId);
    }

    @Override
    public Page<ComunicacaoAux> findByComunicacaoProjetoIdAndAtivoTrueOrderByCreatedAtDesc(Integer projetoId, Pageable pageable) {
        return comunicacaoAuxRepository.findByComunicacao_ProjetoIdAndAtivoTrueOrderByCreatedAtDesc(projetoId, pageable);
    }

    @Override
    public List<ComunicacaoAux> findByComunicacaoClienteIdAndAtivoTrueOrderByCreatedAtDesc(Integer clienteId) {
        return comunicacaoAuxRepository.findByComunicacao_ClienteIdAndAtivoTrueOrderByCreatedAtDesc(clienteId);
    }

    @Override
    public List<ComunicacaoAux> findAll() {
        return comunicacaoAuxRepository.findAll();
    }

    @Override
    public Page<ComunicacaoAux> findAll(Pageable pageable) {
        return comunicacaoAuxRepository.findAll(pageable);
    }

    @Override
    public void deleteByComunicacaoId(Integer comunicacaoId) {
        comunicacaoAuxRepository.deleteByComunicacaoId(comunicacaoId);
    }

    @Override
    public void deleteById(Integer id) {
        comunicacaoAuxRepository.deleteById(id);
    }

    @Override
    public void delete(ComunicacaoAux comunicacaoAux) {
        comunicacaoAuxRepository.delete(comunicacaoAux);
    }

    @Override
    public boolean existsById(Integer id) {
        return comunicacaoAuxRepository.existsById(id);
    }
}
