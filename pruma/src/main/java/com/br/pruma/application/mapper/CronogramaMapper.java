package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.core.domain.Cronograma;
import com.br.pruma.core.domain.Projeto;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CronogramaMapper {

    // Criação: recebe DTO + Projeto carregado
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", source = "projeto")
    Cronograma toEntity(CronogramaRequestDTO dto, Projeto projeto);

    // Update parcial: ignora nulos do DTO, recebe Projeto carregado
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "projeto", source = "projeto")
    void updateEntity(@MappingTarget Cronograma entity, CronogramaRequestDTO dto, Projeto projeto);

    // Response: projeta campos do Projeto
    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "projetoNome", source = "projeto", qualifiedByName = "nomeDoProjeto")
    CronogramaResponseDTO toResponseDTO(Cronograma entity);

    List<CronogramaResponseDTO> toResponseDTO(List<Cronograma> entities);

    @Named("nomeDoProjeto")
    default String nomeDoProjeto(Projeto projeto) {
        return projeto == null ? null : projeto.getNome();
    }
}