package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.UsuarioRequestDTO;
import com.br.pruma.application.dto.response.UsuarioResponseDTO;
import com.br.pruma.application.dto.update.UsuarioUpdateDTO;
import com.br.pruma.core.domain.Usuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "ativo",     ignore = true)
    @Mapping(target = "version",   ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    UsuarioResponseDTO toResponse(Usuario entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "cpf",       ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version",   ignore = true)
    void updateFromDto(UsuarioUpdateDTO dto, @MappingTarget Usuario entity);
}
