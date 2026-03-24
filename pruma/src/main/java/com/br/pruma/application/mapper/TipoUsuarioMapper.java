package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.TipoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.TipoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.TipoUsuarioUpdateDTO;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TipoUsuarioMapper {

    @Mapping(target = "id",             ignore = true)
    @Mapping(target = "dataCriacao",    ignore = true)
    @Mapping(target = "dataAtualizacao",ignore = true)
    @Mapping(target = "versao",         ignore = true)
    TipoUsuario toEntity(TipoUsuarioRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",             ignore = true)
    @Mapping(target = "dataCriacao",    ignore = true)
    @Mapping(target = "dataAtualizacao",ignore = true)
    @Mapping(target = "versao",         ignore = true)
    void updateFromDto(TipoUsuarioUpdateDTO dto, @MappingTarget TipoUsuario entity);

    @Mapping(target = "id",             source = "id")
    @Mapping(target = "descricao",      source = "descricao")
    @Mapping(target = "dataCriacao",    source = "dataCriacao")
    @Mapping(target = "dataAtualizacao",source = "dataAtualizacao")
    @Mapping(target = "versao",         source = "versao")
    @Mapping(target = "ativo",          source = "ativo")
    TipoUsuarioResponseDTO toResponse(TipoUsuario entity);
}
