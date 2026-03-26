package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.core.domain.Cronograma;
import com.br.pruma.core.domain.Projeto;
import org.mapstruct.*;

import java.util.List;
@Mapper(componentModel = "spring")
public interface CronogramaMapper {

    Cronograma toEntity(CronogramaRequestDTO dto);

    CronogramaResponseDTO toResponse(Cronograma entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CronogramaRequestDTO dto, @MappingTarget Cronograma entity);

}