package com.br.pruma.core.repository;

import com.br.pruma.core.domain.ProfissionalDeBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalDeBaseRepository extends JpaRepository<ProfissionalDeBase, Integer> {

    /**
     * Busca profissionais cujo nome contenha o texto informado (case-insensitive).
     */
    List<ProfissionalDeBase> findAllByNomeContainingIgnoreCase(String nome);

    /**
     * Busca profissionais pela especialidade (exata).
     */
    List<ProfissionalDeBase> findAllByEspecialidade(String especialidade);

    /**
     * Busca profissionais cuja especialidade contenha o texto informado (case-insensitive).
     */
    List<ProfissionalDeBase> findAllByEspecialidadeContainingIgnoreCase(String especialidade);

    /**
     * Procura por telefone (exato).
     */
    Optional<ProfissionalDeBase> findByTelefone(String telefone);

    /**
     * Verifica existência por nome exato.
     */
    boolean existsByNome(String nome);
}