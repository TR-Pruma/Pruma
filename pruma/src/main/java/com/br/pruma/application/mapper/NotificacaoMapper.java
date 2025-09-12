package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.NotificacaoRequestDTO;
import com.br.pruma.application.dto.response.NotificacaoResponseDTO;
import com.br.pruma.application.dto.update.NotificacaoUpdateDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Notificacao;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NotificacaoMapper {
    /**
     * Converte DTO de criação em entidade.
     * Ignora chaves, auditoria e associa stubs para FK.
     */
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "version",     ignore = true)
    @Mapping(target = "createdAt",   ignore = true)
    @Mapping(target = "updatedAt",   ignore = true)
    @Mapping(target = "cliente",     source = "clienteCpf",    qualifiedByName = "mapClienteByCpf")
    @Mapping(target = "tipoUsuario", source = "tipoUsuarioId", qualifiedByName = "mapTipoUsuarioById")
    @Mapping(target = "mensagem",    source = "mensagem")
    @Mapping(target = "dataHora",    source = "dataHora")
    @Mapping(target = "lida",        ignore = true)
    Notificacao toEntity(NotificacaoRequestDTO dto);

    /**
     * Atualiza somente campos não-nulos do DTO.
     * Preserva PK, versão e timestamps.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "version",     ignore = true)
    @Mapping(target = "createdAt",   ignore = true)
    @Mapping(target = "updatedAt",   ignore = true)
    @Mapping(target = "cliente",     source = "clienteCpf",    qualifiedByName = "mapClienteByCpf")
    @Mapping(target = "tipoUsuario", source = "tipoUsuarioId", qualifiedByName = "mapTipoUsuarioById")
    @Mapping(target = "mensagem",    source = "mensagem")
    @Mapping(target = "dataHora",    source = "dataHora")
    @Mapping(target = "lida",        source = "lida")
    void updateFromDto(NotificacaoUpdateDTO dto, @MappingTarget Notificacao entity);

    /**
     * Converte entidade em DTO de resposta.
     */
    @Mapping(target = "id",            source = "id")
    @Mapping(target = "clienteCpf",    source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId", source = "tipoUsuario.id")
    @Mapping(target = "mensagem",      source = "mensagem")
    @Mapping(target = "dataHora",      source = "dataHora")
    @Mapping(target = "lida",          source = "lida")
    @Mapping(target = "version",       source = "version")
    @Mapping(target = "createdAt",     source = "createdAt")
    @Mapping(target = "updatedAt",     source = "updatedAt")
    NotificacaoResponseDTO toResponse(Notificacao entity);

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