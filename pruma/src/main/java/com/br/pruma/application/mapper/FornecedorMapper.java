package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.FornecedorRequestDTO;
import com.br.pruma.application.dto.response.FornecedorResponseDTO;
import com.br.pruma.application.dto.update.FornecedorUpdateDTO;
import com.br.pruma.core.domain.Fornecedor;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FornecedorMapper {

    @Mapping(target = "id", ignore = true)
    Fornecedor toEntity(FornecedorRequestDTO request);

    FornecedorResponseDTO toResponse(Fornecedor entity);

    List<FornecedorResponseDTO> toResponseList(List<Fornecedor> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateFromDto(FornecedorUpdateDTO dto, @MappingTarget Fornecedor entity);

}
