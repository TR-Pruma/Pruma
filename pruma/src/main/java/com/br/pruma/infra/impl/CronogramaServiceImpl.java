package com.br.pruma.infra.impl;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.application.mapper.CronogramaMapper;
import com.br.pruma.infra.repository.CronogramaService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Cronograma;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.CronogramaRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CronogramaServiceImpl implements CronogramaService {

    private final CronogramaRepository cronogramaRepository;
    private final ProjetoRepository projetoRepository;
    private final CronogramaMapper cronogramaMapper;

    @Override
    @Transactional
    public CronogramaResponseDTO criar(CronogramaRequestDTO request) {
        validarDatas(request.getDataInicio(), request.getDataFim());

        Projeto projeto = projetoRepository.findByIdAndAtivoTrue(request.getProjetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));

        if (cronogramaRepository.existsByProjetoIdAndDataInicioAndDataFimAndAtivoTrue(
                request.getProjetoId(), request.getDataInicio(), request.getDataFim())) {
            throw new IllegalArgumentException("Já existe um cronograma para este período no projeto");
        }

        Cronograma cronograma = cronogramaMapper.toEntity(request, projeto);
        cronograma = cronogramaRepository.save(cronograma);

        return cronogramaMapper.toResponse(cronograma);
    }

    @Override
    @Transactional(readOnly = true)
    public CronogramaResponseDTO buscarPorId(Integer id) {
        Cronograma cronograma = buscarCronogramaAtivo(id);
        return cronogramaMapper.toResponse(cronograma);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CronogramaResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable) {
        if (!projetoRepository.existsByIdAndAtivoTrue(projetoId)) {
            throw new RecursoNaoEncontradoException("Projeto não encontrado");
        }

        return cronogramaRepository.findByProjetoIdAndAtivoTrueOrderByDataInicioDesc(projetoId, pageable)
                .map(cronogramaMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CronogramaResponseDTO> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        validarDatas(inicio, fim);

        return cronogramaRepository
                .findByDataInicioGreaterThanEqualAndDataFimLessThanEqualAndAtivoTrue(inicio, fim)
                .stream()
                .map(cronogramaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CronogramaResponseDTO> listarPorProjetoOrdenado(Integer projetoId) {
        if (!projetoRepository.existsByIdAndAtivoTrue(projetoId)) {
            throw new RecursoNaoEncontradoException("Projeto não encontrado");
        }

        return cronogramaRepository.findByProjetoIdAndAtivoTrueOrderByDataInicioAsc(projetoId)
                .stream()
                .map(cronogramaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CronogramaResponseDTO atualizar(Integer id, CronogramaRequestDTO request) {
        validarDatas(request.getDataInicio(), request.getDataFim());

        Cronograma cronograma = buscarCronogramaAtivo(id);
        Projeto projeto = projetoRepository.findByIdAndAtivoTrue(request.getProjetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));

        cronogramaMapper.updateEntity(cronograma, request, projeto);
        cronograma = cronogramaRepository.save(cronograma);

        return cronogramaMapper.toResponse(cronograma);
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        Cronograma cronograma = buscarCronogramaAtivo(id);
        cronograma.setAtivo(false);
        cronogramaRepository.save(cronograma);
    }

    private Cronograma buscarCronogramaAtivo(Integer id) {
        return cronogramaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cronograma não encontrado"));
    }

    private void validarDatas(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data de início deve ser anterior à data de fim");
        }
    }
}
