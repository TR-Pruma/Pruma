package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.PreObraRequestDTO;
import com.br.pruma.application.dto.response.PreObraResponseDTO;
import com.br.pruma.application.dto.update.PreObraUpdateDTO;
import com.br.pruma.core.domain.PreObra;
import com.br.pruma.core.domain.Obra;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PreObraMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "obra", source = "obraId", qualifiedByName = "mapObraById")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    PreObra toEntity(PreObraRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "obra", source = "obraId", qualifiedByName = "mapObraById")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateFromDto(PreObraUpdateDTO dto, @MappingTarget PreObra entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "obraId", source = "obra.id")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "version", source = "version")
    PreObraResponseDTO toResponse(PreObra entity);

    @Named("mapObraById")
    default Obra mapObraById(Integer id) {
        if (id == null) return null;
        return Obra.builder().id(id).build();
    }
}

