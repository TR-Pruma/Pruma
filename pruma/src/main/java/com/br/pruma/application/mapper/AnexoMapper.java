package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AnexoRequestDTO;
import com.br.pruma.application.dto.response.AnexoResponseDTO;
import com.br.pruma.core.domain.Anexo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnexoMapper {

    AnexoMapper INSTANCE = Mappers.getMapper(AnexoMapper.class);

    Anexo toEntity(AnexoRequestDTO dto);
    AnexoResponseDTO toResponseDTO(Anexo entity);
}
