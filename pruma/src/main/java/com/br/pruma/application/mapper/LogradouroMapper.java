package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.LogradouroRequestDTO;
import com.br.pruma.application.dto.response.LogradouroResponseDTO;
import com.br.pruma.application.dto.update.LogradouroUpdateDTO;
import com.br.pruma.core.domain.Logradouro;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LogradouroMapper {

    Logradouro toEntity(LogradouroRequestDTO dto);

    LogradouroResponseDTO toResponse(Logradouro entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(LogradouroUpdateDTO dto, @MappingTarget Logradouro entity);
}
