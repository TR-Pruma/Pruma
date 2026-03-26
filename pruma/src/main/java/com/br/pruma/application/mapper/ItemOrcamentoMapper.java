package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ItemOrcamentoRequestDTO;
import com.br.pruma.application.dto.response.ItemOrcamentoResponseDTO;
import com.br.pruma.application.dto.update.ItemOrcamentoUpdateDTO;
import com.br.pruma.core.domain.ItemOrcamento;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ItemOrcamentoMapper {
    /**
     * Converte o DTO de criação em entidade.
     * Ignora campos gerenciados pelo JPA.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orcamento", ignore = true)
    ItemOrcamento toEntity(ItemOrcamentoRequestDTO dto);


    /**
     * Aplica somente campos não-nulos do DTO de atualização na entidade existente.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orcamento", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(ItemOrcamentoUpdateDTO dto, @MappingTarget ItemOrcamento entity);

    /**
     * Converte entidade em DTO de resposta.
     * Inclui cálculo de valorTotal via getter da entidade.
     */
    @Mapping(target = "orcamentoId", source = "orcamento.id")
    @Mapping(target = "valorTotal", expression = "java(entity.getValorTotal())")
    ItemOrcamentoResponseDTO toResponse(ItemOrcamento entity);
}
