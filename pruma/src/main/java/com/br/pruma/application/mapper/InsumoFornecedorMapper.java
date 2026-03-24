package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.InsumoFornecedorRequestDTO;
import com.br.pruma.application.dto.response.InsumoFornecedorResponseDTO;
import com.br.pruma.core.domain.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InsumoFornecedorMapper {

    // ====== DTO → Entidade (create) ======
    @Mapping(target = "id", source = ".", qualifiedByName = "buildCompositeId")
    @Mapping(target = "insumo", source = "insumoId", qualifiedByName = "mapInsumoById")
    @Mapping(target = "fornecedor", source = "fornecedorId", qualifiedByName = "mapFornecedorById")
    @Mapping(target = "version", ignore = true)
    InsumoFornecedor toEntity(InsumoFornecedorRequestDTO dto);

    // ====== Entidade → DTO (response) ======
    @Mapping(target = "insumoId", source = "insumo.id")
    @Mapping(target = "fornecedorId", source = "fornecedor.id")
    @Mapping(target = "preco", source = "preco")
    @Mapping(target = "version", source = "version")
    InsumoFornecedorResponseDTO toResponseDTO(InsumoFornecedor entity);

    // ====== Conversores auxiliares ======
    @Named("mapInsumoById")
    default Insumo mapInsumoById(Integer id) {
        return id == null ? null : Insumo.builder().id(id).build();
    }

    @Named("mapFornecedorById")
    default Fornecedor mapFornecedorById(Integer id) {
        return id == null ? null : Fornecedor.builder().id(id).build();
    }

    @Named("buildCompositeId")
    default InsumoFornecedorAuxId buildCompositeId(InsumoFornecedorRequestDTO dto) {
        if (dto == null) return null;
        Integer insumoId = dto.getInsumoId();
        Integer fornecedorId = dto.getFornecedorId();
        return (insumoId != null && fornecedorId != null) ? new InsumoFornecedorAuxId(insumoId, fornecedorId) : null;
    }

}
