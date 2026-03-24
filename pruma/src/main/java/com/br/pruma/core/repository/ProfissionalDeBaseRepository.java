package com.br.pruma.core.repository;

import com.br.pruma.core.domain.ProfissionalDeBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalDeBaseRepository extends JpaRepository<ProfissionalDeBase, Integer> {

    List<ProfissionalDeBase> findAllByNomeContainingIgnoreCase(String nome);

    List<ProfissionalDeBase> findAllByEspecialidade(String especialidade);

    List<ProfissionalDeBase> findAllByEspecialidadeContainingIgnoreCase(String especialidade);

    Optional<ProfissionalDeBase> findByTelefone(String telefone);

    boolean existsByNome(String nome);

    // cpf é String na entidade (11 dígitos numéricos sem formatação)
    Optional<ProfissionalDeBase> findByCpf(String cpf);
}
