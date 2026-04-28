package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.ProfissionalDeBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de ProfissionalDeBase.
 */
public interface ProfissionalDeBaseRepositoryPort {

    ProfissionalDeBase save(ProfissionalDeBase profissionalDeBase);

    Optional<ProfissionalDeBase> findById(Integer id);

    List<ProfissionalDeBase> findAll();

    Page<ProfissionalDeBase> findAll(Pageable pageable);

    /** Busca profissionais cujo nome contenha o texto informado (case-insensitive). */
    List<ProfissionalDeBase> findAllByNomeContainingIgnoreCase(String nome);

    /** Busca profissionais pela especialidade exata. */
    List<ProfissionalDeBase> findAllByEspecialidade(String especialidade);

    /** Busca profissionais cuja especialidade contenha o texto informado (case-insensitive). */
    List<ProfissionalDeBase> findAllByEspecialidadeContainingIgnoreCase(String especialidade);

    /** Busca profissional pelo telefone. */
    Optional<ProfissionalDeBase> findByTelefone(String telefone);

    /** Busca profissional pelo CPF. */
    Optional<ProfissionalDeBase> findByCpf(String cpf);

    /** Verifica se já existe profissional com o nome informado. */
    boolean existsByNome(String nome);

    void deleteById(Integer id);

    void delete(ProfissionalDeBase profissionalDeBase);

    boolean existsById(Integer id);
}
