package com.br.pruma.application.service.impl;

import com.br.pruma.application.service.ComunicacaoAuxService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.ComunicacaoAux;
import com.br.pruma.core.repository.ComunicacaoAuxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComunicacaoAuxServiceImpl implements ComunicacaoAuxService {

    private final ComunicacaoAuxRepository repository;

    @Override
    public ComunicacaoAux criar(ComunicacaoAux entity) {
        return repository.save(entity);
    }

    @Override
    public ComunicacaoAux buscarPorComunicacaoId(Integer comunicacaoId) {
        return repository.findByComunicacaoIdAndAtivoTrue(comunicacaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Comunicação auxiliar com comunicacaoId " + comunicacaoId + " não encontrada."));
    }

    @Override
    public ComunicacaoAux buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Comunicação auxiliar com ID " + id + " não encontrada."));
    }

    @Override
    public Page<ComunicacaoAux> listarPorProjeto(Integer projetoId, Pageable pageable) {
        return repository.findByComunicacao_ProjetoIdAndAtivoTrueOrderByDataCriacaoDesc(projetoId, pageable);
    }

    @Override
    public List<ComunicacaoAux> listarPorCliente(Integer clienteId) {
        return repository.findByComunicacao_ClienteIdAndAtivoTrueOrderByDataCriacaoDesc(clienteId);
    }

    @Override
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Não é possível deletar. Comunicação auxiliar com ID " + id + " não existe.");
        }
        repository.deleteById(id);
    }
}
