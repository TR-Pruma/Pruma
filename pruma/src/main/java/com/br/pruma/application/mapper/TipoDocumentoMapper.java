package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.TipoDocumentoRequestDTO;
import com.br.pruma.application.dto.response.TipoDocumentoResponseDTO;
import com.br.pruma.core.domain.TipoDocumento;
import org.mapstruct.*;
import java.util.List;

@Mapper(
        componentModel                   = "spring",
        unmappedTargetPolicy             = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TipoDocumentoMapper {

    /**
     * Cria uma nova entidade a partir do DTO.
     * O campo id é gerenciado pelo banco, então é ignorado aqui.
     */
    @Mapping(target = "id", ignore = true)
    TipoDocumento toEntity(TipoDocumentoRequestDTO dto);

    /**
     * Converte entidade em DTO de resposta.
     */
    @Mapping(source = "id",        target = "id")
    @Mapping(source = "descricao", target = "descricao")
    TipoDocumentoResponseDTO toDTO(TipoDocumento entity);

    /**
     * Atualização parcial: apenas campos não-nulos do DTO sobrescrevem a entidade.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "descricao", source = "descricao")
    void updateFromDto(TipoDocumentoRequestDTO dto, @MappingTarget TipoDocumento entity);

    /**
     * Conversão de listas de entidade → DTO.
     */
    List<TipoDocumentoResponseDTO> toDTOList(List<TipoDocumento> entities);
}