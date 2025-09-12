package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ObraRequestDTO;
import com.br.pruma.application.dto.response.ObraResponseDTO;
import com.br.pruma.application.dto.update.ObraUpdateDTO;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.Projeto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ObraMapper {

    /**
     * Converte ObraRequestDTO em entidade Obra.
     * Ignora campos de auditoria e chave primária.
     */
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "version",    ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    @Mapping(target = "projeto",    source = "projetoId", qualifiedByName = "mapProjetoById")
    @Mapping(target = "descricao",  source = "descricao")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "dataFim",    source = "dataFim")
    Obra toEntity(ObraRequestDTO dto);

    /**
     * Atualiza parcialmente uma Obra existente a partir de ObraUpdateDTO.
     * Campos nulos no DTO são ignorados.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "version",    ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    @Mapping(target = "projeto",    source = "projetoId", qualifiedByName = "mapProjetoById")
    @Mapping(target = "descricao",  source = "descricao")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "dataFim",    source = "dataFim")
    void updateFromDto(ObraUpdateDTO dto, @MappingTarget Obra entity);

    /**
     * Converte entidade Obra em ObraResponseDTO.
     */
    @Mapping(target = "id",         source = "id")
    @Mapping(target = "projetoId",  source = "projeto.id")
    @Mapping(target = "descricao",  source = "descricao")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "dataFim",    source = "dataFim")
    @Mapping(target = "version",    source = "version")
    @Mapping(target = "createdAt",  source = "createdAt")
    @Mapping(target = "updatedAt",  source = "updatedAt")
    ObraResponseDTO toResponse(Obra entity);

    /**
     * Stub para associação de Projeto por ID.
     */
    @Named("mapProjetoById")
    default Projeto mapProjetoById(Integer id) {
        if (id == null) {
            return null;
        }
        return Projeto.builder()
                .id(id)
                .build();
    }
}