package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.RelatorioRequestDTO;
import com.br.pruma.application.dto.response.RelatorioResponseDTO;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.Relatorio;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RelatorioMapper {

    /**
     * Converte RequestDTO para entidade Relatorio.
     * Usa Obra.ofId para criar referência apenas com o identificador.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "obra", expression = "java(Obra.ofId(dto.obraId()))")
    Relatorio toEntity(RelatorioRequestDTO dto);

    /**
     * Converte entidade Relatorio para ResponseDTO.
     * Extrai apenas os identificadores da associação.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "obra.id", target = "obraId")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "dataCriacao", target = "dataCriacao")
    RelatorioResponseDTO toDTO(Relatorio entity);

    /**
     * Atualiza a entidade existente com os campos não-nulos do DTO.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "obra", expression = "java(dto.obraId() == null ? existing.getObra() : Obra.ofId(dto.obraId()))")
    void updateFromDto(RelatorioRequestDTO dto, @MappingTarget Relatorio existing);

    /**
     * Lista de entidades para lista de DTOs.
     */
    List<RelatorioResponseDTO> toDTOList(List<Relatorio> entities);
}
