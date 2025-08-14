package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.EquipamentoProjetoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoProjetoResponseDTO;
import com.br.pruma.core.domain.EquipamentoProjeto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EquipamentoProjetoMapper {
    @Mapping(target = "equipamento.id", source = "equipamentoId")
    @Mapping(target = "projeto.id", source = "projetoId")
    EquipamentoProjeto toEntity(EquipamentoProjetoRequestDTO dto);

    @Mapping(target = "equipamentoId", source = "equipamento.id")
    @Mapping(target = "equipamentoNome", source = "equipamento.nome")
    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "projetoNome", source = "projeto.nome")
    EquipamentoProjetoResponseDTO toResponseDTO(EquipamentoProjeto entity);

}
