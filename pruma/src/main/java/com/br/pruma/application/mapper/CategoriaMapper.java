package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.CategoriaRequestDTO;
import com.br.pruma.application.dto.response.CategoriaResponseDTO;
import com.br.pruma.application.dto.update.CategoriaUpdateDTO;
import com.br.pruma.core.domain.Categoria;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    Categoria toEntity(CategoriaRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CategoriaUpdateDTO dto, @MappingTarget Categoria entity);

    CategoriaResponseDTO toResponse(Categoria entity);
}
