package com.br.pruma.application.service.impl;

import com.br.pruma.application.service.ComunicacaoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.repository.ComunicacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComunicacaoServiceImpl implements ComunicacaoService {

    private final ComunicacaoRepository repository;

    @Override
    public Comunicacao criar(Comunicacao comunicacao) {
        return repository.save(comunicacao);
    }

    @Override
    public Comunicacao buscarPorId(Integer id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Comunicação com ID " + id + " não encontrada."));
    }

    @Override
    public Page<Comunicacao> listarPorProjeto(Integer projetoId, Pageable pageable) {
        return repository.findByProjetoIdAndAtivoTrueOrderByDataCriacaoDesc(projetoId, pageable);
    }

    @Override
    public List<Comunicacao> listarPorCliente(Integer clienteId) {
        return repository.findByClienteIdAndAtivoTrueOrderByDataCriacaoDesc(clienteId);
    }

    @Override
    public Page<Comunicacao> listarPorProjetoECliente(Integer projetoId, Integer clienteId, Pageable pageable) {
        return repository.findByProjetoIdAndClienteIdAndAtivoTrueOrderByDataCriacaoDesc(projetoId, clienteId, pageable);
    }

    @Override
    public Comunicacao atualizar(Integer id, Comunicacao comunicacao) {
        Comunicacao existente = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Comunicação com ID " + id + " não encontrada."));
        existente.setMensagem(comunicacao.getMensagem());
        existente.setTipoRemetente(comunicacao.getTipoRemetente());
        return repository.save(existente);
    }

    @Override
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Não é possível deletar. Comunicação com ID " + id + " não existe.");
        }
        repository.deleteById(id);
    }
}
