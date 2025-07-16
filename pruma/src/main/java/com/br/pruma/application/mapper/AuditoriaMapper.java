package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.core.domain.Auditoria;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuditoriaMapper {

    AuditoriaMapper INSTANCE = Mappers.getMapper(AuditoriaMapper.class);

    Auditoria toEntity(AuditoriaRequestDTO dto);

    AuditoriaResponseDTO toResponseDTO(Auditoria auditoria);
}
