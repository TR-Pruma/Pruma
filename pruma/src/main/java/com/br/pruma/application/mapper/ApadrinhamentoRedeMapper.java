package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ApadrinhamentoRedeRequestDTO;
import com.br.pruma.application.dto.response.ApadrinhamentoRedeResponseDTO;
import com.br.pruma.application.dto.update.ApadrinhamentoRedeUpdateDTO;
import com.br.pruma.core.domain.ApadrinhamentoRede;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ApadrinhamentoRedeMapper {

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "padrinho",  ignore = true)   // resolvido no ServiceImpl via RepositoryPort
    @Mapping(target = "afilhado",  ignore = true)   // resolvido no ServiceImpl via RepositoryPort
    @Mapping(target = "status",    ignore = true)   // default "ATIVO" definido no Builder da entidade
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ApadrinhamentoRede toEntity(ApadrinhamentoRedeRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "padrinho",  ignore = true)
    @Mapping(target = "afilhado",  ignore = true)
    @Mapping(target = "dataInicio",ignore = true)   // campo imutável pós-criação
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(ApadrinhamentoRedeUpdateDTO dto, @MappingTarget ApadrinhamentoRede entity);

    @Mapping(target = "padrinhoId",   source = "padrinho.id")
    @Mapping(target = "padrinhoNome", source = "padrinho.nome")
    @Mapping(target = "afilhadoId",   source = "afilhado.id")
    @Mapping(target = "afilhadoNome", source = "afilhado.nome")
    ApadrinhamentoRedeResponseDTO toResponse(ApadrinhamentoRede entity);
}