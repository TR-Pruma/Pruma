package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.RequisicaoMaterialRequestDTO;
import com.br.pruma.application.dto.response.RequisicaoMaterialResponseDTO;
import com.br.pruma.core.domain.RequisicaoMaterial;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RequisicaoMaterialMapper {

    // Obra nao tem campo 'nome' (tem 'descricao'); Material nao tem 'nome' (tem 'descricao')
    @Mapping(source = "obra.id",          target = "obraId")
    @Mapping(source = "obra.descricao",   target = "obraNome")
    @Mapping(source = "material.id",      target = "materialId")
    @Mapping(source = "material.descricao", target = "materialNome")
    RequisicaoMaterialResponseDTO toDTO(RequisicaoMaterial entity);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "obra",      ignore = true)
    @Mapping(target = "material",  ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    RequisicaoMaterial toEntity(RequisicaoMaterialRequestDTO request);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "obra",      ignore = true)
    @Mapping(target = "material",  ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDto(RequisicaoMaterialRequestDTO request, @MappingTarget RequisicaoMaterial entity);
}
