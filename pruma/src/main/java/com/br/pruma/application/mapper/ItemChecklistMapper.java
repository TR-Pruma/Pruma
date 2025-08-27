package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ItemChecklistRequestDTO;
import com.br.pruma.application.dto.request.ItemChecklistUpdateDTO;
import com.br.pruma.application.dto.response.ItemChecklistResponseDTO;
import com.br.pruma.core.domain.ItemChecklist;
import com.br.pruma.core.enums.StatusItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ItemChecklistMapper {

    // Converte RequestDTO em entidade (não mapeia checklist)
    @Mapping(target = "checklist", ignore = true)
    ItemChecklist toEntity(ItemChecklistRequestDTO dto);

    // Atualiza apenas propriedades não-nulas de UpdateDTO na entidade
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "checklist", ignore = true)
    void updateEntityFromDto(ItemChecklistUpdateDTO dto, @MappingTarget ItemChecklist entity);

    // Converte entidade em ResponseDTO, mapeando checklist.id para checklistId
    @Mapping(target = "checklistId", source = "checklist.id")
    ItemChecklistResponseDTO toResponse(ItemChecklist entity);
}

