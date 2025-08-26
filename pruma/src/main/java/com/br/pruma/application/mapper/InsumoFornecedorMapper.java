package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.InsumoFornecedorRequestDTO;
import com.br.pruma.application.dto.response.InsumoFornecedorResponseDTO;
import com.br.pruma.core.domain.InsumoFornecedor;
import com.br.pruma.core.domain.InsumoFornecedorAux;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InsumoFornecedorMapper {

    @Mapping(target = "id.insumoId",     source = "request.insumoId")
    @Mapping(target = "id.fornecedorId", source = "request.fornecedorId")
    @Mapping(target = "preco",           source = "request.preco")
    InsumoFornecedor toEntity(InsumoFornecedorRequestDTO request);

    @Mapping(target = "insumoId",     source = "entity.id.insumoId")
    @Mapping(target = "fornecedorId", source = "entity.id.fornecedorId")
    @Mapping(target = "preco",        source = "entity.preco")
    @Mapping(target = "version",      source = "entity.version")
    InsumoFornecedorResponseDTO toResponse(InsumoFornecedor entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id.insumoId",     ignore = true)
    @Mapping(target = "id.fornecedorId", ignore = true)
    @Mapping(target = "preco",           source = "request.preco")
    void updateFromRequest(InsumoFornecedorRequestDTO request,
                           @MappingTarget InsumoFornecedor entity);



}
