package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.ComunicacaoAux;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de ComunicacaoAux.
 */
public interface ComunicacaoAuxRepositoryPort {

    ComunicacaoAux save(ComunicacaoAux comunicacaoAux);

    Optional<ComunicacaoAux> findById(Integer id);

    /** Busca aux ativo pelo id da comunicação. */
    Optional<ComunicacaoAux> findByComunicacaoIdAndAtivoTrue(Integer comunicacaoId);

    /** Lista aux ativos de um projeto, ordenados por data de criação desc. */
    Page<ComunicacaoAux> findByComunicacao_ProjetoIdAndAtivoTrueOrderByCreatedAtDesc(Integer projetoId, Pageable pageable);

    /** Lista aux ativos de um cliente, ordenados por data de criação desc. */
    List<ComunicacaoAux> findByComunicacao_ClienteIdAndAtivoTrueOrderByCreatedAtDesc(Integer clienteId);

    List<ComunicacaoAux> findAll();

    Page<ComunicacaoAux> findAll(Pageable pageable);

    /** Remove todos os registros associados a uma comunicação. */
    void deleteByComunicacaoId(Integer comunicacaoId);

    void deleteById(Integer id);

    void delete(ComunicacaoAux comunicacaoAux);

    boolean existsById(Integer id);
}
