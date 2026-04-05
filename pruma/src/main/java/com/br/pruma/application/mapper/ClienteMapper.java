package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.application.dto.update.ClienteUpdateDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Endereco;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
@DecoratedWith(ClienteMapperDecorator.class)
public interface ClienteMapper {

    @Mapping(source = "dto.cpf",      target = "cpf")
    @Mapping(source = "dto.nome",     target = "nome")
    @Mapping(source = "dto.email",    target = "email")
    @Mapping(source = "dto.telefone", target = "telefone")
    @Mapping(source = "dto.senha",    target = "senha")
    @Mapping(source = "dto.ativo",    target = "ativo")
    @Mapping(source = "endereco",     target = "endereco")
    Cliente toEntity(ClienteRequestDTO dto, Endereco endereco);

    ClienteResponseDTO toDto(Cliente cliente);

    /**
     * Atualização completa via PUT (troca endereço e senha).
     * Campos nulos no DTO são ignorados.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "dto.cpf",      target = "cpf")
    @Mapping(source = "dto.nome",     target = "nome")
    @Mapping(source = "dto.email",    target = "email")
    @Mapping(source = "dto.telefone", target = "telefone")
    @Mapping(source = "dto.senha",    target = "senha")
    @Mapping(source = "dto.ativo",    target = "ativo")
    @Mapping(source = "endereco",     target = "endereco")
    void updateFromDto(ClienteRequestDTO dto, Endereco endereco, @MappingTarget Cliente entity);

    /**
     * Atualização parcial via PATCH (sem trocar endereço nem senha).
     * Campos nulos no DTO são ignorados.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "senha",    ignore = true)
    @Mapping(target = "cpf",      source = "cpfCnpj")
    void updateFromDto(ClienteUpdateDTO dto, @MappingTarget Cliente entity);
}
