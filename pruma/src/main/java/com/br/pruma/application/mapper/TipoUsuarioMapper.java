package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.TipoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.TipoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.TipoUsuarioUpdateDTO;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TipoUsuarioMapper {

    TipoUsuario toEntity(TipoUsuarioRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TipoUsuarioUpdateDTO dto, @MappingTarget TipoUsuario entity);

    TipoUsuarioResponseDTO toResponse(TipoUsuario entity);
}
