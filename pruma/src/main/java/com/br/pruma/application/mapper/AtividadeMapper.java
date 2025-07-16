package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.core.domain.Atividade;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AtividadeMapper {

    AtividadeMapper INSTANCE = Mappers.getMapper(AtividadeMapper.class);

    Atividade toEntity(AtividadeRequestDTO dto);
    AtividadeResponseDTO toResponseDTO(Atividade entity);
}
