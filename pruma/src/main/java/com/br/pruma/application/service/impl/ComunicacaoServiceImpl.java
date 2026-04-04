package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ComunicacaoRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoResponseDTO;
import com.br.pruma.application.mapper.ComunicacaoMapper;
import com.br.pruma.application.service.ComunicacaoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.ComunicacaoRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ComunicacaoServiceImpl implements ComunicacaoService {

    private final ComunicacaoRepository comunicacaoRepository;
    private final ProjetoRepository projetoRepository;
    private final ClienteRepository clienteRepository;
    private final ComunicacaoMapper comunicacaoMapper;

    @Override
    public ComunicacaoResponseDTO criar(ComunicacaoRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Projeto com ID " + dto.getProjetoId() + " não encontrado."));
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente com ID " + dto.getClienteId() + " não encontrado."));
        Comunicacao entity = comunicacaoMapper.toEntity(dto, projeto, cliente);
        return comunicacaoMapper.toDTO(comunicacaoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ComunicacaoResponseDTO buscarPorId(Integer id) {
        return comunicacaoRepository.findByIdAndAtivoTrue(id)
                .map(comunicacaoMapper::toDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Comunicação com ID " + id + " não encontrada."));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComunicacaoResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable) {
        return comunicacaoRepository
                .findByProjetoIdAndAtivoTrueOrderByDataCriacaoDesc(projetoId, pageable)
                .map(comunicacaoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComunicacaoResponseDTO> listarPorCliente(Integer clienteId) {
        return comunicacaoRepository
                .findByClienteIdAndAtivoTrueOrderByDataCriacaoDesc(clienteId)
                .stream()
                .map(comunicacaoMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComunicacaoResponseDTO> listarPorProjetoECliente(
            Integer projetoId, Integer clienteId, Pageable pageable) {
        return comunicacaoRepository
                .findByProjetoIdAndClienteIdAndAtivoTrueOrderByDataCriacaoDesc(projetoId, clienteId, pageable)
                .map(comunicacaoMapper::toDTO);
    }

    @Override
    public ComunicacaoResponseDTO atualizar(Integer id, ComunicacaoRequestDTO dto) {
        Comunicacao existente = comunicacaoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Comunicação com ID " + id + " não encontrada."));
        comunicacaoMapper.updateEntity(existente, dto);
        return comunicacaoMapper.toDTO(comunicacaoRepository.save(existente));
    }

    @Override
    public void deletar(Integer id) {
        if (!comunicacaoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Não é possível deletar. Comunicação com ID " + id + " não existe.");
        }
        comunicacaoRepository.deleteById(id);
    }
}
