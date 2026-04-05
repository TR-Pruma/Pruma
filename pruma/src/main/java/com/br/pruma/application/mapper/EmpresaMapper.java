package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.application.dto.update.EmpresaUpdateDTO;
import com.br.pruma.core.domain.Empresa;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EmpresaMapper {

    Empresa toEntity(EmpresaRequestDTO dto);

    EmpresaResponseDTO toResponseDto(Empresa entity);

    List<EmpresaResponseDTO> toResponseDtoList(List<Empresa> entities);

    /**
     * Atualização completa via PUT.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(EmpresaRequestDTO dto, @MappingTarget Empresa entity);

    /**
     * Atualização parcial via PATCH.
     * EmpresaUpdateDTO possui: nome, cnpj, email, telefone.
     * A entidade não tem email/telefone — campos não mapeados são ignorados (IGNORE policy).
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "razaoSocial", source = "nome")
    void updateFromDto(EmpresaUpdateDTO dto, @MappingTarget Empresa entity);
}
