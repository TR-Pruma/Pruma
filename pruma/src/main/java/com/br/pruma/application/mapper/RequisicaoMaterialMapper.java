package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.RequisicaoMaterialRequestDTO;
import com.br.pruma.application.dto.response.RequisicaoMaterialResponseDTO;
import com.br.pruma.application.dto.update.RequisicaoMaterialUpdateDTO;
import com.br.pruma.core.domain.RequisicaoMaterial;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RequisicaoMaterialMapper {

    @Mapping(source = "obra.id",            target = "obraId")
    @Mapping(source = "obra.descricao",     target = "obraNome")
    @Mapping(source = "material.id",        target = "materialId")
    @Mapping(source = "material.descricao", target = "materialNome")
    RequisicaoMaterialResponseDTO toResponse(RequisicaoMaterial entity);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "obra",      ignore = true)
    @Mapping(target = "material",  ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    RequisicaoMaterial toEntity(RequisicaoMaterialRequestDTO dto);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "obra",      ignore = true)
    @Mapping(target = "material",  ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(RequisicaoMaterialUpdateDTO dto, @MappingTarget RequisicaoMaterial entity);
}
