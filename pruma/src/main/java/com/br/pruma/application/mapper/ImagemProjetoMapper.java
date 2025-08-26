package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ImagemProjetoRequestDTO;
import com.br.pruma.application.dto.response.ImagemProjetoResponseDTO;
import com.br.pruma.core.domain.ImagemProjeto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImagemProjetoMapper {
    @Mapping(source = "projeto.id", target = "projetoId")
    ImagemProjetoResponseDTO toResponseDTO(ImagemProjeto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", expression = "java( new Projeto(dto.projetoId()) )")
    ImagemProjeto toEntity(ImagemProjetoRequestDTO dto);
}