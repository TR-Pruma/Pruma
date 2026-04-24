package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.MaterialUtilizadoRequestDTO;
import com.br.pruma.application.dto.response.MaterialUtilizadoResponseDTO;
import com.br.pruma.application.dto.update.MaterialUtilizadoUpdateDTO;
import com.br.pruma.core.domain.MaterialUtilizado;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MaterialUtilizadoMapper {

    MaterialUtilizado toEntity(MaterialUtilizadoRequestDTO dto);

    MaterialUtilizadoResponseDTO toResponse(MaterialUtilizado entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(MaterialUtilizadoUpdateDTO dto, @MappingTarget MaterialUtilizado entity);
}
