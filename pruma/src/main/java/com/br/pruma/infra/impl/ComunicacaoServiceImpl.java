package com.br.pruma.infra.impl;

import com.br.pruma.core.repository.ComunicacaoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.repository.ComunicacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComunicacaoServiceImpl implements ComunicacaoService {

    private final ComunicacaoRepository comunicacaoRepository;

    @Override
    @Transactional
    public Comunicacao criar(Comunicacao comunicacao) {
        comunicacao.setAtivo(true);
        return comunicacaoRepository.save(comunicacao);
    }

    @Override
    @Transactional(readOnly = true)
    public Comunicacao buscarPorId(Integer id) {
        return comunicacaoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Comunicação não encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comunicacao> listarPorProjeto(Integer projetoId, Pageable pageable) {
        return comunicacaoRepository.findByProjetoIdAndAtivoTrueOrderByDataHoraDesc(projetoId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comunicacao> listarPorCliente(Integer clienteId) {
        return comunicacaoRepository.findByClienteIdAndAtivoTrueOrderByDataHoraDesc(clienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comunicacao> listarPorProjetoECliente(Integer projetoId, Integer clienteId, Pageable pageable) {
        return comunicacaoRepository.findByProjetoIdAndClienteIdAndAtivoTrueOrderByDataHoraDesc(projetoId, clienteId, pageable);
    }

    @Override
    @Transactional
    public Comunicacao atualizar(Integer id, Comunicacao comunicacao) {
        Comunicacao comunicacaoExistente = buscarPorId(id);

        comunicacaoExistente.setMensagem(comunicacao.getMensagem());
        comunicacaoExistente.setTipoRemetente(comunicacao.getTipoRemetente());

        return comunicacaoRepository.save(comunicacaoExistente);
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        Comunicacao comunicacao = buscarPorId(id);
        comunicacao.setAtivo(false);
        comunicacaoRepository.save(comunicacao);
    }
}
