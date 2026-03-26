package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ProjetoRequestDTO;
import com.br.pruma.application.dto.response.ProjetoResponseDTO;
import com.br.pruma.application.dto.update.ProjetoUpdateDTO;
import com.br.pruma.core.domain.Projeto;
import org.mapstruct.*;

/**
 * Mapper MapStruct para Projeto.
 * Campos herdados de AuditableEntity (createdAt, updatedAt, version, ativo) sao ignorados
 * no toEntity/updateFromDto pois nao sao aceitos pelo @Builder do Lombok em subclasses.
 */
@Mapper(componentModel = "spring")
public interface ProjetoMapper {

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "obras",     ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version",   ignore = true)
    @Mapping(target = "ativo",     ignore = true)
    Projeto toEntity(ProjetoRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "obras",     ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version",   ignore = true)
    @Mapping(target = "ativo",     ignore = true)
    void updateFromDto(ProjetoUpdateDTO dto, @MappingTarget Projeto entity);

    @Mapping(target = "id",          source = "id")
    @Mapping(target = "nome",         source = "nome")
    @Mapping(target = "descricao",    source = "descricao")
    @Mapping(target = "dataCriacao",  source = "dataCriacao")
    @Mapping(target = "createdAt",    source = "createdAt")
    @Mapping(target = "updatedAt",    source = "updatedAt")
    @Mapping(target = "version",      source = "version")
    ProjetoResponseDTO toResponse(Projeto entity);
}
