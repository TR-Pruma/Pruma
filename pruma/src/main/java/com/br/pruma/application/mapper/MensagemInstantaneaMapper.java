package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.MensagemInstantaneaRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaUpdateDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.MensagemInstantanea;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MensagemInstantaneaMapper {

    @Mapping(target = "id",               ignore = true)
    @Mapping(target = "version",          ignore = true)
    @Mapping(target = "createdAt",        ignore = true)
    @Mapping(target = "updatedAt",        ignore = true)
    @Mapping(target = "cliente",          source = "clienteCpf",     qualifiedByName = "mapClienteByCpf")
    @Mapping(target = "tipoUsuario",      source = "tipoUsuarioName", qualifiedByName = "mapTipoUsuarioByName")
    @Mapping(target = "destinatarioId",   source = "destinatarioId")
    @Mapping(target = "tipoDestinatario", source = "tipoDestinatario")
    @Mapping(target = "conteudo",         source = "conteudo")
    @Mapping(target = "dataHora",         source = "dataHora")
    MensagemInstantanea toEntity(MensagemInstantaneaRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",               ignore = true)
    @Mapping(target = "version",          ignore = true)
    @Mapping(target = "createdAt",        ignore = true)
    @Mapping(target = "updatedAt",        ignore = true)
    @Mapping(target = "cliente",          source = "clienteCpf",     qualifiedByName = "mapClienteByCpf")
    @Mapping(target = "tipoUsuario",      source = "tipoUsuarioName", qualifiedByName = "mapTipoUsuarioByName")
    @Mapping(target = "destinatarioId",   source = "destinatarioId")
    @Mapping(target = "tipoDestinatario", source = "tipoDestinatario")
    @Mapping(target = "conteudo",         source = "conteudo")
    @Mapping(target = "dataHora",         source = "dataHora")
    void updateFromDto(MensagemInstantaneaUpdateDTO dto, @MappingTarget MensagemInstantanea entity);

    @Mapping(target = "id",               source = "id")
    @Mapping(target = "clienteCpf",       source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId",    source = "tipoUsuario", qualifiedByName = "tipoUsuarioToName")
    @Mapping(target = "destinatarioId",   source = "destinatarioId")
    @Mapping(target = "tipoDestinatario", source = "tipoDestinatario")
    @Mapping(target = "conteudo",         source = "conteudo")
    @Mapping(target = "dataHora",         source = "dataHora")
    @Mapping(target = "version",          source = "version")
    @Mapping(target = "createdAt",        source = "createdAt")
    @Mapping(target = "updatedAt",        source = "updatedAt")
    MensagemInstantaneaResponseDTO toResponse(MensagemInstantanea entity);

    @Named("mapClienteByCpf")
    default Cliente mapClienteByCpf(String cpf) {
        return cpf == null ? null : Cliente.builder().cpf(cpf).build();
    }

    @Named("mapTipoUsuarioByName")
    default TipoUsuario mapTipoUsuarioByName(String name) {
        return name == null ? null : TipoUsuario.valueOf(name);
    }

    @Named("tipoUsuarioToName")
    default String tipoUsuarioToName(TipoUsuario tipo) {
        return tipo == null ? null : tipo.name();
    }
}
