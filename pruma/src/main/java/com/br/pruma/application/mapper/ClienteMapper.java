package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.core.domain.Cliente;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    @Mapping(target = "endereco.id", source = "enderecoId")
    Cliente toEntity(ClienteRequestDTO dto);

    @Mapping(target = "enderecoId", source = "endereco.id")
    ClienteResponseDTO toDto(Cliente cliente);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "endereco.id", source = "enderecoId")
    void updateFromDto(ClienteRequestDTO dto, @MappingTarget Cliente entity);
}


