package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.application.dto.update.CronogramaUpdateDTO;
import com.br.pruma.core.domain.Cronograma;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CronogramaMapper {

    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "projeto", ignore = true)
    Cronograma toEntity(CronogramaRequestDTO dto);

    CronogramaResponseDTO toResponse(Cronograma entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "projeto", ignore = true)
    void updateFromDto(CronogramaUpdateDTO dto, @MappingTarget Cronograma entity);
}
