package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.response.ItemChecklistResponseDTO;
import com.br.pruma.core.domain.ItemChecklist;
import com.br.pruma.core.enums.StatusItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ItemChecklistMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "ordem", source = "ordem")
    @Mapping(target = "observacao", source = "observacao")
    @Mapping(target = "concluido", expression = "java(StatusItem.CONCLUIDO.equals(item.getStatus()))")
    ItemChecklistResponseDTO toResponseDTO(ItemChecklist item);

    @Named("itemToId")
    default Integer itemToId(ItemChecklist item) {
        return item != null ? item.getId() : null;
    }

    @Named("itemToDescricao")
    default String itemToDescricao(ItemChecklist item) {
        return item != null ? item.getDescricao() : null;
    }
}
