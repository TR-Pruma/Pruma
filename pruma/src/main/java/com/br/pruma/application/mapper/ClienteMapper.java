package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Endereco;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(source = "dto.ativo", target = "ativo")
    @Mapping(source = "dto.cpf", target = "cpf")
    @Mapping(source = "dto.nome", target = "nome")
    @Mapping(source = "dto.email", target = "email")
    @Mapping(source = "dto.telefone", target = "telefone")
    @Mapping(source = "dto.senha", target = "senha")
    @Mapping(source = "endereco", target = "endereco")
    Cliente toEntity(ClienteRequestDTO dto, Endereco endereco);

    ClienteResponseDTO toDto(Cliente cliente);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "dto.ativo", target = "ativo")
    @Mapping(source = "dto.cpf", target = "cpf")
    @Mapping(source = "dto.nome", target = "nome")
    @Mapping(source = "dto.email", target = "email")
    @Mapping(source = "dto.telefone", target = "telefone")
    @Mapping(source = "dto.senha", target = "senha")
    @Mapping(source = "endereco", target = "endereco")
    void updateEntity(@MappingTarget Cliente entity, ClienteRequestDTO dto, Endereco endereco);
}




