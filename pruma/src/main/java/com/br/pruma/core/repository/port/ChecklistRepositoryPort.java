package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Checklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Checklist.
 */
public interface ChecklistRepositoryPort {

    Checklist save(Checklist checklist);

    Optional<Checklist> findById(Integer id);

    /** Busca checklist por id carregando os itens associados. */
    Optional<Checklist> findByIdWithItens(Integer id);

    List<Checklist> findAll();

    Page<Checklist> findAll(Pageable pageable);

    /** Busca todos os checklists de um projeto, carregando itens associados. */
    List<Checklist> findByProjetoIdWithItens(Integer projetoId);

    /** Soft delete: marca checklist como inativo. */
    void softDelete(Integer id);

    /** Verifica se já existe checklist ativo com mesmo nome no projeto. */
    boolean existsByNomeAndProjetoIdAndAtivoTrue(String nome, Integer projetoId);

    /** Verifica duplicidade excluindo um id específico. */
    boolean existsByNomeAndProjetoIdAndIdNot(String nome, Integer projetoId, Integer id);

    void deleteById(Integer id);

    void delete(Checklist checklist);

    boolean existsById(Integer id);
}
