package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.FaseCronogramaRequestDTO;
import com.br.pruma.application.dto.response.FaseCronogramaResponseDTO;
import com.br.pruma.core.domain.FaseCronograma;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FaseCronogramaMapper {
    @Mapping(target = "cronograma.id", source = "cronogramaId")
    FaseCronograma toEntity(FaseCronogramaRequestDTO dto);

    @Mapping(target = "cronogramaId", source = "cronograma.id")
    @Mapping(target = "cronogramaNome", source = "cronograma.nome")
    FaseCronogramaResponseDTO toResponseDTO(FaseCronograma entity);

}
