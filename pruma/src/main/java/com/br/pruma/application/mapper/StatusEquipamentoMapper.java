package com.br.pruma.application.mapper;


import com.br.pruma.application.dto.request.StatusEquipamentoRequestDTO;
import com.br.pruma.application.dto.response.StatusEquipamentoResponseDTO;
import com.br.pruma.application.dto.update.StatusEquipamentoUpdateDTO;
import com.br.pruma.core.domain.StatusEquipamento;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StatusEquipamentoMapper {

    StatusEquipamentoResponseDTO toDTO(StatusEquipamento entity);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    StatusEquipamento toEntity(StatusEquipamentoRequestDTO request);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDto(StatusEquipamentoRequestDTO request, @MappingTarget StatusEquipamento entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDto(StatusEquipamentoUpdateDTO dto, @MappingTarget StatusEquipamento entity);
}