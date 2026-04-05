package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.FaseCronogramaRequestDTO;
import com.br.pruma.application.dto.response.FaseCronogramaResponseDTO;
import com.br.pruma.application.dto.update.FaseCronogramaUpdateDTO;
import com.br.pruma.core.domain.FaseCronograma;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FaseCronogramaMapper {

    @Mapping(target = "cronograma.id", source = "cronogramaId")
    FaseCronograma toEntity(FaseCronogramaRequestDTO dto);

    @Mapping(target = "cronogramaId", source = "cronograma.id")
    @Mapping(target = "cronogramaNome", source = "cronograma.nome")
    FaseCronogramaResponseDTO toResponseDTO(FaseCronograma entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cronograma", ignore = true)
    void updateFromDto(FaseCronogramaUpdateDTO dto, @MappingTarget FaseCronograma entity);

}
