package com.br.pruma.infra.impl;

import com.br.pruma.infra.repository.ComunicacaoAuxService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.ComunicacaoAux;
import com.br.pruma.core.repository.ComunicacaoAuxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComunicacaoAuxServiceImpl implements ComunicacaoAuxService {

    private final ComunicacaoAuxRepository comunicacaoAuxRepository;

    @Override
    @Transactional
    public ComunicacaoAux criar(ComunicacaoAux comunicacaoAux) {
        comunicacaoAux.setAtivo(true);
        return comunicacaoAuxRepository.save(comunicacaoAux);
    }

    @Override
    @Transactional(readOnly = true)
    public ComunicacaoAux buscarPorId(Integer comunicacaoId) {
        return comunicacaoAuxRepository.findByComunicacaoIdAndAtivoTrue(comunicacaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Comunicação auxiliar não encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComunicacaoAux> listarPorProjeto(Integer projetoId, Pageable pageable) {
        return comunicacaoAuxRepository.findByComunicacao_ProjetoIdAndAtivoTrueOrderByDataCriacaoDesc(projetoId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComunicacaoAux> listarPorCliente(Integer clienteId) {
        return comunicacaoAuxRepository.findByComunicacao_ClienteIdAndAtivoTrueOrderByDataCriacaoDesc(clienteId);
    }

    @Override
    @Transactional
    public void deletar(Integer comunicacaoId) {
        comunicacaoAuxRepository.deleteByComunicacaoId(comunicacaoId);
    }

    @Override
    @Transactional(readOnly = true)
    public ComunicacaoAux buscarPorComunicacaoId(Integer comunicacaoId) {
        return comunicacaoAuxRepository.findByComunicacaoIdAndAtivoTrue(comunicacaoId)
                .orElse(null);
    }

}
