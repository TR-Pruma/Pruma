package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ComunicacaoAuxRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoAuxResponseDTO;
import com.br.pruma.application.mapper.ComunicacaoAuxMapper;
import com.br.pruma.application.service.ComunicacaoAuxService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.domain.ComunicacaoAux;
import com.br.pruma.core.repository.ComunicacaoAuxRepository;
import com.br.pruma.core.repository.ComunicacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ComunicacaoAuxServiceImpl implements ComunicacaoAuxService {

    private final ComunicacaoAuxRepository repository;
    private final ComunicacaoRepository comunicacaoRepository;
    private final ComunicacaoAuxMapper mapper;

    @Override
    public ComunicacaoAuxResponseDTO criar(ComunicacaoAuxRequestDTO dto) {
        Comunicacao comunicacao = comunicacaoRepository.findByIdAndAtivoTrue(dto.getComunicacaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Comunicação com ID " + dto.getComunicacaoId() + " não encontrada."));
        ComunicacaoAux entity = mapper.toEntity(dto, comunicacao);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ComunicacaoAuxResponseDTO buscarPorComunicacaoId(Integer comunicacaoId) {
        return repository.findByComunicacaoIdAndAtivoTrue(comunicacaoId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Comunicação auxiliar com comunicacaoId " + comunicacaoId + " não encontrada."));
    }

    @Override
    @Transactional(readOnly = true)
    public ComunicacaoAuxResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Comunicação auxiliar com ID " + id + " não encontrada."));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComunicacaoAuxResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable) {
        return repository
                .findByComunicacao_ProjetoIdAndAtivoTrueOrderByCreatedAtDesc(projetoId, pageable)
                .map(mapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComunicacaoAuxResponseDTO> listarPorCliente(Integer clienteId) {
        return repository
                .findByComunicacao_ClienteIdAndAtivoTrueOrderByCreatedAtDesc(clienteId)
                .stream()
                .map(mapper::toDTO)
                .toList();
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
