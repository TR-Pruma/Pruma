package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.EnderecoRequestDTO;
import com.br.pruma.application.dto.response.EnderecoResponseDTO;
import com.br.pruma.core.domain.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    @Mapping(target = "empresa.cnpj", source = "empresaCnpj")
    Endereco toEntity(EnderecoRequestDTO dto);

    @Mapping(target = "empresaCnpj", source = "empresa.cnpj")
    EnderecoResponseDTO toResponseDTO(Endereco entity);
}
