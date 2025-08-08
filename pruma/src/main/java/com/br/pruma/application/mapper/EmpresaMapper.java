package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.core.domain.Empresa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {
    Empresa toEntity(EmpresaRequestDTO dto);
    EmpresaResponseDTO toResponseDTO(Empresa entity);
}
