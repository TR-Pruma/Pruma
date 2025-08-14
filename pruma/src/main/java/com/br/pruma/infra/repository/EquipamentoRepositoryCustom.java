package com.br.pruma.infra.repository;

import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.enums.StatusEquipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface EquipamentoRepositoryCustom {

    Page<Equipamento> searchIncludingInativos(
            String nomeLike,
            StatusEquipamento status,
            Boolean ativo, // null = todos; true = apenas ativos; false = apenas inativos
            LocalDateTime criadoDe,
            LocalDateTime criadoAte,
            Pageable pageable
    );

    int softDeleteManyByIds(List<Integer> ids);

    int reativarManyByIds(List<Integer> ids);
}


