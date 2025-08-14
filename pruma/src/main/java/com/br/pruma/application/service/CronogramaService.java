package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface CronogramaService {
    CronogramaResponseDTO criar(CronogramaRequestDTO request);
    CronogramaResponseDTO buscarPorId(Integer id);
    Page<CronogramaResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable);
    List<CronogramaResponseDTO> listarPorPeriodo(LocalDate inicio, LocalDate fim);
    List<CronogramaResponseDTO> listarPorProjetoOrdenado(Integer projetoId);
    CronogramaResponseDTO atualizar(Integer id, CronogramaRequestDTO request);
    void deletar(Integer id);
