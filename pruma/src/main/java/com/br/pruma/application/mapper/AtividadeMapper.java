package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.application.dto.update.AtividadeUpdateDTO;
import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.enums.StatusAtividade;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AtividadeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", source = "projeto", qualifiedByName = "projetoIdToProjeto")
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "materiaisUtilizados", ignore = true)
    Atividade toEntity(AtividadeRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "projeto", source = "projeto", qualifiedByName = "projetoIdToProjeto")
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "materiaisUtilizados", ignore = true)
    void updateFromDto(AtividadeUpdateDTO dto, @MappingTarget Atividade entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "projeto", source = "projeto.id")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "dataFim", source = "dataFim")
    AtividadeResponseDTO toResponse(Atividade entity);

    List<AtividadeResponseDTO> toResponseList(List<Atividade> entities);

    @Named("projetoIdToProjeto")
    default Projeto projetoIdToProjeto(Integer projetoId) {
        if (projetoId == null) return null;
        return Projeto.ofId(projetoId);
    }

    @Named("stringToStatus")
    default StatusAtividade stringToStatus(String status) {
        if (status == null) return null;
        try {
            return StatusAtividade.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Named("statusToString")
    default String statusToString(StatusAtividade status) {
        return status == null ? null : status.name();
    }
}
