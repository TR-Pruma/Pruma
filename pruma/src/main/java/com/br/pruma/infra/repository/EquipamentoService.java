package com.br.pruma.infra.repository;


import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.enums.StatusEquipamento;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EquipamentoService {
    private final EquipamentoRepository repository;

    public Page<Equipamento> listar(
            String nome,
            StatusEquipamento status,
            Boolean ativo,
            LocalDateTime de,
            LocalDateTime ate,
            Pageable pageable
    ) {
        return repository.searchIncludingInativos(nome, status, ativo, de, ate, pageable);
    }

    @Transactional
    public int inativarEmLote(List<Integer> ids) {
        return repository.softDeleteManyByIds(ids);
    }

    @Transactional
    public int reativarEmLote(List<Integer> ids) {
        return repository.reativarManyByIds(ids);
    }
}

