package com.br.pruma.application.mapper;


import com.br.pruma.application.dto.request.ProjetoCategoriaRequestDTO;
import com.br.pruma.application.dto.response.ProjetoCategoriaResponseDTO;
import com.br.pruma.core.domain.ProjetoCategoria;
import org.mapstruct.*;
import java.util.List;

@Mapper(
        componentModel                   = "spring",
        unmappedTargetPolicy             = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProjetoCategoriaMapper {

    /**
     * Cria uma entidade a partir do DTO de request.
     * O id é gerenciado pelo banco, portanto ignorado na criação.
     */
    @Mapping(target = "id", ignore = true)
    ProjetoCategoria toEntity(ProjetoCategoriaRequestDTO dto);

    /**
     * Converte entidade para DTO de resposta.
     */
    @Mapping(source = "id",    target = "id")
    @Mapping(source = "nome",  target = "nome")
    @Mapping(source = "descricao", target = "descricao")
    ProjetoCategoriaResponseDTO toDTO(ProjetoCategoria entity);

    /**
     * Atualiza a entidade existente com os campos não-nulos do DTO.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateFromDto(ProjetoCategoriaRequestDTO dto, @MappingTarget ProjetoCategoria entity);

    /**
     * Lista de entidades para lista de DTOs.
     */
    List<ProjetoCategoriaResponseDTO> toDTOList(List<ProjetoCategoria> entities);
}

