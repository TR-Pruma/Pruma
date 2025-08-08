package com.br.pruma.infra.repository;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface CronogramaService {

    /**
     * Cria um novo cronograma
     */
    CronogramaResponseDTO criar(CronogramaRequestDTO request);

    /**
     * Busca um cronograma por ID
     */
    CronogramaResponseDTO buscarPorId(Integer id);

    /**
     * Lista todos os cronogramas de um projeto de forma paginada
     */
    Page<CronogramaResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable);

    /**
     * Lista cronogramas por período
     */
    List<CronogramaResponseDTO> listarPorPeriodo(LocalDate inicio, LocalDate fim);

    /**
     * Lista cronogramas de um projeto ordenados por data de início
     */
    List<CronogramaResponseDTO> listarPorProjetoOrdenado(Integer projetoId);

    /**
     * Atualiza um cronograma existente
     */
    CronogramaResponseDTO atualizar(Integer id, CronogramaRequestDTO request);

    /**
     * Exclui logicamente um cronograma
     */
    void deletar(Integer id);
}

