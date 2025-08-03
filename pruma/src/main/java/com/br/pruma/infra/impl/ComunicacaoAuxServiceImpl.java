package com.br.pruma.infra.impl;

import com.br.pruma.application.dto.request.ComunicacaoAuxRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoAuxResponseDTO;
import com.br.pruma.application.mapper.ComunicacaoAuxMapper;
import com.br.pruma.core.repository.ComunicacaoAuxService;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.domain.ComunicacaoAux;
import com.br.pruma.core.repository.ComunicacaoAuxRepository;
import com.br.pruma.core.repository.ComunicacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComunicacaoAuxServiceImpl implements ComunicacaoAuxService {

    private final ComunicacaoAuxRepository comunicacaoAuxRepository;
    private final ComunicacaoRepository comunicacaoRepository;
    private final ComunicacaoAuxMapper comunicacaoAuxMapper;

    @Override
    @Transactional
    public ComunicacaoAuxResponseDTO criar(ComunicacaoAuxRequestDTO request) {
        Comunicacao comunicacao = comunicacaoRepository.findByIdAndAtivoTrue(request.getComunicacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Comunicação não encontrada"));

        ComunicacaoAux comunicacaoAux = comunicacaoAuxMapper.toEntity(request, comunicacao);
        comunicacaoAux = comunicacaoAuxRepository.save(comunicacaoAux);

        return comunicacaoAuxMapper.toDTO(comunicacaoAux);
    }

    @Override
    @Transactional(readOnly = true)
    public ComunicacaoAuxResponseDTO buscarPorComunicacaoId(Integer comunicacaoId) {
        ComunicacaoAux comunicacaoAux = comunicacaoAuxRepository.findByComunicacaoIdAndAtivoTrue(comunicacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Informação auxiliar da comunicação não encontrada"));

        return comunicacaoAuxMapper.toDTO(comunicacaoAux);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComunicacaoAuxResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable) {
        return comunicacaoAuxRepository
                .findByComunicacao_ProjetoIdAndAtivoTrueOrderByDataCriacaoDesc(projetoId, pageable)
                .map(comunicacaoAuxMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComunicacaoAuxResponseDTO> listarPorCliente(Integer clienteId) {
        return comunicacaoAuxRepository
                .findByComunicacao_ClienteIdAndAtivoTrueOrderByDataCriacaoDesc(clienteId)
                .stream()
                .map(comunicacaoAuxMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletar(Integer comunicacaoId) {
        ComunicacaoAux comunicacaoAux = comunicacaoAuxRepository.findByComunicacaoIdAndAtivoTrue(comunicacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Informação auxiliar da comunicação não encontrada"));

        comunicacaoAux.setAtivo(false);
        comunicacaoAuxRepository.save(comunicacaoAux);
    }
}
