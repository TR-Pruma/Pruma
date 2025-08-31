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

    /**
     * Converte o DTO de criação em entidade.
     * Ignora campos gerenciados pelo JPA/auditoria e associa stubs para FK.
     */
    @Mapping(target = "id",             ignore = true)
    @Mapping(target = "version",        ignore = true)
    @Mapping(target = "createdAt",      ignore = true)
    @Mapping(target = "updatedAt",      ignore = true)
    @Mapping(target = "cliente",        source = "clienteCpf",    qualifiedByName = "mapClienteByCpf")
    @Mapping(target = "tipoUsuario",    source = "tipoUsuarioId", qualifiedByName = "mapTipoUsuarioById")
    @Mapping(target = "destinatarioId", source = "destinatarioId")
    @Mapping(target = "tipoDestinatario", source = "tipoDestinatario")
    @Mapping(target = "conteudo",       source = "conteudo")
    @Mapping(target = "dataHora",       source = "dataHora")
    MensagemInstantanea toEntity(MensagemInstantaneaRequestDTO dto);

    /**
     * Atualiza somente campos não-nulos do DTO.
     * Preserva ID, versão e timestamps.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",             ignore = true)
    @Mapping(target = "version",        ignore = true)
    @Mapping(target = "createdAt",      ignore = true)
    @Mapping(target = "updatedAt",      ignore = true)
    @Mapping(target = "cliente",        source = "clienteCpf",    qualifiedByName = "mapClienteByCpf")
    @Mapping(target = "tipoUsuario",    source = "tipoUsuarioId", qualifiedByName = "mapTipoUsuarioById")
    @Mapping(target = "destinatarioId", source = "destinatarioId")
    @Mapping(target = "tipoDestinatario", source = "tipoDestinatario")
    @Mapping(target = "conteudo",       source = "conteudo")
    @Mapping(target = "dataHora",       source = "dataHora")
    void updateFromDto(MensagemInstantaneaUpdateDTO dto, @MappingTarget MensagemInstantanea entity);

    /**
     * Converte entidade em DTO de resposta, extraindo chaves e auditoria.
     */
    @Mapping(target = "id",              source = "id")
    @Mapping(target = "clienteCpf",      source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId",   source = "tipoUsuario.id")
    @Mapping(target = "destinatarioId",  source = "destinatarioId")
    @Mapping(target = "tipoDestinatario",source = "tipoDestinatario")
    @Mapping(target = "conteudo",        source = "conteudo")
    @Mapping(target = "dataHora",        source = "dataHora")
    @Mapping(target = "version",         source = "version")
    @Mapping(target = "createdAt",       source = "createdAt")
    @Mapping(target = "updatedAt",       source = "updatedAt")
    MensagemInstantaneaResponseDTO toResponse(MensagemInstantanea entity);

    /**
     * Stub de Cliente para associação.
     */
    @Named("mapClienteByCpf")
    default Cliente mapClienteByCpf(Long cpf) {
        if (cpf == null) {
            return null;
        }
        return Cliente.builder()
                .cpf(String.valueOf(cpf))
                .build();
    }

    /**
     * Stub de TipoUsuario para associação.
     */
    @Named("mapTipoUsuarioById")
    default TipoUsuario mapTipoUsuarioById(Integer id) {
        if (id == null) {
            return null;
        }
        return TipoUsuario.builder()
                .id(id)
                .build();
    }
}