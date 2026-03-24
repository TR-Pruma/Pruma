package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.core.domain.Empresa;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EmpresaMapper {

    /**
     * Cria entidade a partir do DTO de request.
     * Ignora o id, que é gerado pelo banco.
     */

    Empresa toEntity(EmpresaRequestDTO dto);

    /**
     * Converte entidade para DTO de resposta.
     */
    EmpresaResponseDTO toResponseDto(Empresa entity);

    /**
     * Atualiza parcialmente a entidade existente com os campos não-nulos do DTO.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(EmpresaRequestDTO dto, @MappingTarget Empresa entity);

    /**
     * Converte lista de entidades para lista de DTOs.
     */
    List<EmpresaResponseDTO> toResponseDtoList(List<Empresa> entities);
}
