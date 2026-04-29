package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.TarefaRequestDTO;
import com.br.pruma.application.dto.response.TarefaResponseDTO;
import com.br.pruma.application.dto.update.TarefaUpdateDTO;
import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.domain.Tarefa;
import com.br.pruma.core.enums.StatusTarefa;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "atividade", source = "atividadeId", qualifiedByName = "atividadeIdToAtividade")
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Tarefa toEntity(TarefaRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "atividade", source = "atividadeId", qualifiedByName = "atividadeIdToAtividade")
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateFromDto(TarefaUpdateDTO dto, @MappingTarget Tarefa entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "atividade", source = "atividade.id")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    @Mapping(target = "dataCriacao", source = "dataCriacao")
    @Mapping(target = "dataConclusao", source = "dataConclusao")
    TarefaResponseDTO toResponse(Tarefa entity);

    List<TarefaResponseDTO> toResponseList(List<Tarefa> entities);

    @Named("atividadeIdToAtividade")
    default Atividade atividadeIdToAtividade(Integer atividadeId) {
        if (atividadeId == null) return null;
        return Atividade.ofId(atividadeId);
    }

    @Named("stringToStatus")
    default StatusTarefa stringToStatus(String status) {
        if (status == null) return null;
        try {
            return StatusTarefa.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Named("statusToString")
    default String statusToString(StatusTarefa status) {
        return status == null ? null : status.name();
    }
}
