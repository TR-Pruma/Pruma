package com.br.pruma.core.repository;

import com.br.pruma.core.domain.ApadrinhamentoRede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApadrinhamentoRedeRepository extends JpaRepository<ApadrinhamentoRede, Long> {

    List<ApadrinhamentoRede> findAllByPadrinhoId(Long padrinhoId);

    List<ApadrinhamentoRede> findAllByAfilhadoId(Long afilhadoId);

    List<ApadrinhamentoRede> findAllByStatus(String status);

    boolean existsByPadrinhoIdAndAfilhadoIdAndStatus(Long padrinhoId, Long afilhadoId, String status);
}