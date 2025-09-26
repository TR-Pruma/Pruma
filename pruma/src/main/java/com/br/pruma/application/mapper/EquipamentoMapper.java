package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.update.EquipamentoAtivoUpdateDTO;
import com.br.pruma.application.dto.response.EquipamentoListDTO;
import com.br.pruma.core.domain.Equipamento;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipamentoMapper {

    @Mapping(target = "id", ignore = true)
    Equipamento toEntity(EquipamentoRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(EquipamentoRequestDTO dto, @MappingTarget Equipamento entity);

    EquipamentoListDTO toListDto(Equipamento entity);

    List<EquipamentoListDTO> toListDtoList(List<Equipamento> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAtivoFromDto(EquipamentoAtivoUpdateDTO dto, @MappingTarget Equipamento entity);
}



