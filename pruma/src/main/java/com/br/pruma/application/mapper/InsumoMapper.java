package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.InsumoRequestDTO;
import com.br.pruma.application.dto.response.InsumoResponseDTO;
import com.br.pruma.core.domain.Insumo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InsumoMapper {
    @Mapping(target = "unidadeMedida", source = "unidadeMedida")
    Insumo toEntity(InsumoRequestDTO dto);

    @Mapping(target = "unidadeMedida", source = "entity.unidadeMedida.name")
    InsumoResponseDTO toDto(Insumo entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "unidadeMedida", source = "unidadeMedida")
    void updateFromDto(InsumoRequestDTO dto, @MappingTarget Insumo entity);

}
