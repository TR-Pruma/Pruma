package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.LogAlteracaoRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoResponseDTO;
import com.br.pruma.core.domain.LogAlteracao;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LogAlteracaoMapper {

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "projeto",     ignore = true)
    @Mapping(target = "cliente",     ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "dataHora",    ignore = true)
    LogAlteracao toEntity(LogAlteracaoRequestDTO dto);

    @Mapping(target = "projetoId",     source = "projeto.id")
    @Mapping(target = "clienteCpf",    source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId", source = "tipoUsuario", qualifiedByName = "tipoUsuarioToName")
    LogAlteracaoResponseDTO toResponse(LogAlteracao entity);

    @Named("tipoUsuarioToName")
    default String tipoUsuarioToName(TipoUsuario tipo) {
        return tipo == null ? null : tipo.name();
    }
}
