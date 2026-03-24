package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.FornecedorRequestDTO;
import com.br.pruma.application.dto.response.FornecedorResponseDTO;
import com.br.pruma.core.domain.Fornecedor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {
    Fornecedor toEntity(FornecedorRequestDTO request);

    FornecedorResponseDTO toResponse(Fornecedor entity);

    List<FornecedorResponseDTO> toResponseList(List<Fornecedor> entities);
}