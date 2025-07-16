package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ChecklistRequestDTO;
import com.br.pruma.application.dto.response.ChecklistResponseDTO;
import com.br.pruma.core.domain.Checklist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChecklistMapper {

    ChecklistMapper INSTANCE = Mappers.getMapper(ChecklistMapper.class);

    Checklist toEntity(ChecklistRequestDTO dto);

    ChecklistResponseDTO toResponseDTO(Checklist checklist);
}
