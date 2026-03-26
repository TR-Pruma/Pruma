package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.enums.StatusAtividade;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AtividadeMapper {

    // ====== DTO → Entidade (create) ======
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", source = "projeto", qualifiedByName = "projetoIdToProjeto")
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "materiaisUtilizados", ignore = true)
    Atividade toEntity(AtividadeRequestDTO dto);

    // ====== DTO → Entidade (update parcial) ======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "projeto", source = "projeto", qualifiedByName = "projetoIdToProjeto")
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "materiaisUtilizados", ignore = true)
    void updateFromDto(AtividadeRequestDTO dto, @MappingTarget Atividade entity);

    // ====== Entidade → DTO (response) ======
    @Mapping(target = "id", source = "id")
    @Mapping(target = "projeto", source = "projeto.id")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "dataFim", source = "dataFim")
    AtividadeResponseDTO toResponseDTO(Atividade entity);

    List<AtividadeResponseDTO> toResponseList(List<Atividade> entities);

    // ====== Conversores auxiliares ======
    @Named("projetoIdToProjeto")
    default Projeto projetoIdToProjeto(Integer projetoId) {
        if (projetoId == null) return null;
        return Projeto.builder().id(projetoId).build();
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

