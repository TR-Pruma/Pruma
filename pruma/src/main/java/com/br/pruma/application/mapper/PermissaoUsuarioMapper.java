package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.PermissaoUsuario;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PermissaoUsuarioMapper {

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "cliente",     source = "clienteCpf",     qualifiedByName = "clienteFromCpf")
    @Mapping(target = "tipoUsuario", source = "tipoUsuarioName", qualifiedByName = "tipoUsuarioFromName")
    PermissaoUsuario toEntity(PermissaoUsuarioRequestDTO dto);

    @Mapping(target = "clienteCpf",           source = "cliente.cpf")
    @Mapping(target = "clienteNome",          source = "cliente.nome")
    @Mapping(target = "tipoUsuarioId",        source = "tipoUsuario", qualifiedByName = "tipoUsuarioToName")
    @Mapping(target = "tipoUsuarioDescricao", source = "tipoUsuario", qualifiedByName = "tipoUsuarioToName")
    PermissaoUsuarioResponseDTO toResponseDTO(PermissaoUsuario entity);

    @Named("clienteFromCpf")
    default Cliente clienteFromCpf(String cpf) {
        return cpf == null ? null : Cliente.builder().cpf(cpf).build();
    }

    @Named("tipoUsuarioFromName")
    default TipoUsuario tipoUsuarioFromName(String name) {
        return name == null ? null : TipoUsuario.valueOf(name);
    }

    @Named("tipoUsuarioToName")
    default String tipoUsuarioToName(TipoUsuario tipo) {
        return tipo == null ? null : tipo.name();
    }
}
