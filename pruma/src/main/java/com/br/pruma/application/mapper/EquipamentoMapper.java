package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.core.domain.Equipamento;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EquipamentoMapper {
    Equipamento toEntity(EquipamentoRequestDTO dto);

    EquipamentoResponseDTO toResponseDTO(Equipamento entity);

    void updateEntityFromDTO(EquipamentoRequestDTO dto, @MappingTarget Equipamento entity);
}
