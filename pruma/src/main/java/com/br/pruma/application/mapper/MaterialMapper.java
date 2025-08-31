package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.MaterialRequestDTO;
import com.br.pruma.application.dto.response.MaterialResponseDTO;
import com.br.pruma.application.dto.update.MaterialUpdateDTO;
import com.br.pruma.core.domain.Material;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    /**
     * Converte o DTO de criação em entidade.
     * Ignora campos gerenciados pelo JPA e auditoria.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Material toEntity(MaterialRequestDTO dto);

    /**
     * Atualiza somente campos não-nulos do DTO de atualização.
     * Mantém intactos ID, versão e carimbos de auditoria.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(MaterialUpdateDTO dto, @MappingTarget Material entity);

    /**
     * Converte a entidade em DTO de resposta.
     */
    MaterialResponseDTO toResponse(Material entity);
}

