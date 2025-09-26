package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {
    // Aqui você pode definir queries customizadas, por exemplo:
    // List<Atividade> findByNomeContainingIgnoreCase(String nome);
}
