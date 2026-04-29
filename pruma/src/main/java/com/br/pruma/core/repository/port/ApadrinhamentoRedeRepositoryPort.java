package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.ApadrinhamentoRede;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApadrinhamentoRedeRepositoryPort {

    ApadrinhamentoRede save(ApadrinhamentoRede apadrinhamentoRede);

    Optional<ApadrinhamentoRede> findById(Long id);

    List<ApadrinhamentoRede> findAll();

    Page<ApadrinhamentoRede> findAll(Pageable pageable);

    /** Retorna todos os apadrinhamentos de um determinado padrinho. */
    List<ApadrinhamentoRede> findAllByPadrinhoId(Long padrinhoId);

    /** Retorna todos os apadrinhamentos de um determinado afilhado. */
    List<ApadrinhamentoRede> findAllByAfilhadoId(Long afilhadoId);

    /** Retorna todos os apadrinhamentos com o status informado (ATIVO | ENCERRADO). */
    List<ApadrinhamentoRede> findAllByStatus(String status);

    /** Verifica se já existe um vínculo ativo entre padrinho e afilhado. */
    boolean existsByPadrinhoIdAndAfilhadoIdAndStatus(Long padrinhoId, Long afilhadoId, String status);

    void deleteById(Long id);

    boolean existsById(Long id);
}