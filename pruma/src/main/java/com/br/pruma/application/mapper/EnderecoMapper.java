package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.EnderecoRequestDTO;
import com.br.pruma.application.dto.response.EnderecoResponseDTO;
import com.br.pruma.core.domain.Endereco;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EnderecoMapper {

    @Mapping(target = "empresa.cnpj", source = "empresaCnpj")
    Endereco toEntity(EnderecoRequestDTO dto);

    @Mapping(target = "empresaCnpj", source = "empresa.cnpj")
    EnderecoResponseDTO toResponseDTO(Endereco entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "empresa.cnpj", source = "empresaCnpj")
    void updateFromDto(EnderecoRequestDTO dto, @MappingTarget Endereco entity);
}
