package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.LogradouroRequestDTO;
import com.br.pruma.application.dto.response.LogradouroResponseDTO;
import com.br.pruma.application.dto.update.LogradouroUpdateDTO;
import com.br.pruma.core.domain.Logradouro;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LogradouroMapper {

    /**
     * Cria uma entidade Logradouro a partir do DTO de criação.
     * Ignora campos gerenciados pelo JPA e auditoria.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Logradouro toEntity(LogradouroRequestDTO dto);

    /**
     * Atualiza apenas campos não-nulos do DTO de atualização.
     * Mantém intactos ID, versão e carimbos de auditoria.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(LogradouroUpdateDTO dto, @MappingTarget Logradouro entity);

    /**
     * Converte entidade em DTO de resposta.
     */
    LogradouroResponseDTO toResponse(Logradouro entity);
}

