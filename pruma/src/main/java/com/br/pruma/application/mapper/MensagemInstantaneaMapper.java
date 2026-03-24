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
    @Mapping(target = "cliente",          source = "clienteCpf",    qualifiedByName = "mapClienteByCpf")
    @Mapping(target = "tipoUsuario",      source = "tipoUsuarioId", qualifiedByName = "mapTipoUsuarioById")
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
    @Mapping(target = "cliente",          source = "clienteCpf",    qualifiedByName = "mapClienteByCpf")
    @Mapping(target = "tipoUsuario",      source = "tipoUsuarioId", qualifiedByName = "mapTipoUsuarioById")
    @Mapping(target = "destinatarioId",   source = "destinatarioId")
    @Mapping(target = "tipoDestinatario", source = "tipoDestinatario")
    @Mapping(target = "conteudo",         source = "conteudo")
    @Mapping(target = "dataHora",         source = "dataHora")
    void updateFromDto(MensagemInstantaneaUpdateDTO dto, @MappingTarget MensagemInstantanea entity);

    @Mapping(target = "id",               source = "id")
    @Mapping(target = "clienteCpf",       source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId",    source = "tipoUsuario.id")
    @Mapping(target = "destinatarioId",   source = "destinatarioId")
    @Mapping(target = "tipoDestinatario", source = "tipoDestinatario")
    @Mapping(target = "conteudo",         source = "conteudo")
    @Mapping(target = "dataHora",         source = "dataHora")
    @Mapping(target = "version",          source = "version")
    @Mapping(target = "createdAt",        source = "createdAt")
    @Mapping(target = "updatedAt",        source = "updatedAt")
    MensagemInstantaneaResponseDTO toResponse(MensagemInstantanea entity);

    @Named("mapClienteByCpf")
    default Cliente mapClienteByCpf(Long cpf) {
        return cpf == null ? null : Cliente.builder().cpf(String.valueOf(cpf)).build();
    }

    @Named("mapTipoUsuarioById")
    default TipoUsuario mapTipoUsuarioById(Integer id) {
        return id == null ? null : TipoUsuario.builder().id(id).build();
    }
}
