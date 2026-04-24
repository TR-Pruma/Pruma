package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Projeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Projeto.
 */
public interface ProjetoRepositoryPort {

    Projeto save(Projeto projeto);

    Optional<Projeto> findById(Integer id);

    List<Projeto> findAll();

    Page<Projeto> findAll(Pageable pageable);

    /** Busca projetos cujo nome contenha o texto informado (case-insensitive). */
    List<Projeto> findAllByNomeContainingIgnoreCase(String nome);

    /** Busca projeto pelo nome exato. */
    Optional<Projeto> findByNome(String nome);

    /** Verifica existência por nome exato. */
    boolean existsByNome(String nome);

    /** Lista projetos com data de criação entre as datas informadas, com paginação. */
    Page<Projeto> findAllByDataCriacaoBetween(LocalDate start, LocalDate end, Pageable pageable);

    /** Lista projetos com data de criação exata, com paginação. */
    Page<Projeto> findAllByDataCriacao(LocalDate date, Pageable pageable);

    /** Busca projeto ativo pelo id. */
    Optional<Projeto> findByIdAndAtivoTrue(Integer projetoId);

    /** Verifica se existe projeto ativo com o id informado. */
    boolean existsByIdAndAtivoTrue(Integer projetoId);

    void deleteById(Integer id);

    void delete(Projeto projeto);

    boolean existsById(Integer id);
}
