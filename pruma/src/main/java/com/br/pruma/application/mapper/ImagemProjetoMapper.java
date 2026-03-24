package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ImagemProjetoRequestDTO;
import com.br.pruma.application.dto.response.ImagemProjetoResponseDTO;
import com.br.pruma.core.domain.ImagemProjeto;
import com.br.pruma.core.domain.Projeto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ImagemProjetoMapper {

    @Mapping(source = "projeto.id", target = "projetoId")
    ImagemProjetoResponseDTO toResponseDTO(ImagemProjeto entity);

    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "projeto", source = "projetoId", qualifiedByName = "projetoFromId")
    ImagemProjeto toEntity(ImagemProjetoRequestDTO dto);

    @Named("projetoFromId")
    default Projeto projetoFromId(Integer id) {
        return Projeto.ofId(id);
    }
}
