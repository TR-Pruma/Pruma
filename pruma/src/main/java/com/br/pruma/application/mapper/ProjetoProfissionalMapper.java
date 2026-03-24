package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ProjetoProfissionalRequestDTO;
import com.br.pruma.application.dto.response.ProjetoProfissionalResponseDTO;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.ProjetoProfissional;
import com.br.pruma.core.domain.ProfissionalDeBase;
import org.mapstruct.*;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProjetoProfissionalMapper {

    /**
     * Converte RequestDTO para entidade.
     * Usa expressões para criar referências de entidade apenas com o id (ofId).
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", expression = "java(Projeto.ofId(dto.projetoId()))")
    @Mapping(target = "profissional", expression = "java(ProfissionalDeBase.ofId(dto.profissionalId()))")
    ProjetoProfissional toEntity(ProjetoProfissionalRequestDTO dto);

    /**
     * Converte entidade para ResponseDTO.
     * Extrai apenas os identificadores das associações.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "projeto.id", target = "projetoId")
    @Mapping(source = "profissional.id", target = "profissionalId")
    ProjetoProfissionalResponseDTO toDTO(ProjetoProfissional entity);

    /**
     * Atualiza a entidade existente com os campos não-nulos do DTO.
     * Se projetoId/profissionalId forem nulos, mantém as referências atuais.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", expression = "java(dto.projetoId() == null ? existing.getProjeto() : Projeto.ofId(dto.projetoId()))")
    @Mapping(target = "profissional", expression = "java(dto.profissionalId() == null ? existing.getProfissional() : ProfissionalDeBase.ofId(dto.profissionalId()))")
    void updateFromDto(ProjetoProfissionalRequestDTO dto, @MappingTarget ProjetoProfissional existing);

    /**
     * Lista de entidades para lista de DTOs.
     */
    List<ProjetoProfissionalResponseDTO> toDTOList(List<ProjetoProfissional> entities);
}
