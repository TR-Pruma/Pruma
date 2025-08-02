package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ChecklistRequestDTO;
import com.br.pruma.application.dto.response.ChecklistResponseDTO;
import com.br.pruma.core.domain.Checklist;
import com.br.pruma.core.domain.Projeto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ItemChecklistMapper.class})
public interface ChecklistMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "projeto", source = "projetoId", qualifiedByName = "projetoIdToProjeto")
    @Mapping(target = "itens", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    Checklist toEntity(ChecklistRequestDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "projetoNome", source = "projeto.nome")
    @Mapping(target = "itens", source = "itens")
    @Mapping(target = "ativo", source = "ativo")
    @Mapping(target = "dataCriacao", source = "dataCriacao")
    @Mapping(target = "dataAtualizacao", source = "dataAtualizacao")
    @Mapping(target = "percentualConcluido", expression = "java(checklist.getPercentualConcluido())")
    ChecklistResponseDTO toResponseDTO(Checklist checklist);

    @Named("projetoIdToProjeto")
    default Projeto projetoIdToProjeto(Integer projetoId) {
        if (projetoId == null) {
            return null;
        }
        Projeto projeto = new Projeto();
        projeto.setId(projetoId);
        return projeto;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "projeto", source = "projetoId", qualifiedByName = "projetoIdToProjeto")
    @Mapping(target = "itens", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(@MappingTarget Checklist entity, ChecklistRequestDTO dto);
}
