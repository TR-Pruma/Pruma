package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.LogAlteracaoRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoResponseDTO;
import com.br.pruma.core.domain.LogAlteracao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LogAlteracaoMapper {

    /**
     * Converte o DTO de criação em entidade.
     * Ignora campos gerenciados pelo JPA e relacionamentos,
     * para que o serviço injete Projeto, Cliente e TipoUsuario.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "dataHora", ignore = true)
    LogAlteracao toEntity(LogAlteracaoRequestDTO dto);

    /**
     * Converte a entidade em DTO de resposta,
     * extraindo IDs e CPF das associações.
     */
    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "clienteCpf", source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId", source = "tipoUsuario.id")
    LogAlteracaoResponseDTO toResponse(LogAlteracao entity);

}
