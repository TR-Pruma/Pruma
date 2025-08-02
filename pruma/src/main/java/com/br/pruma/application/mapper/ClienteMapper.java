package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Endereco;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public abstract class ClienteMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "endereco", source = "enderecoId", qualifiedByName = "idToEndereco")
    @Mapping(target = "senha", expression = "java(passwordEncoder.encode(dto.getSenha()))")
    @Mapping(target = "ativo", constant = "true")
    public abstract Cliente toEntity(ClienteRequestDTO dto);

    @Mapping(target = "endereco", source = "endereco")
    public abstract ClienteResponseDTO toResponseDTO(Cliente cliente);

    @Named("idToEndereco")
    protected Endereco idToEndereco(Integer id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "endereco", source = "enderecoId", qualifiedByName = "idToEndereco")
    @Mapping(target = "senha", expression = "java(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : entity.getSenha())")
    public abstract void updateEntity(@MappingTarget Cliente entity, ClienteRequestDTO dto);
}
