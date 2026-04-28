package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.LogAlteracaoAuxRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoAuxResponseDTO;
import com.br.pruma.application.dto.update.LogAlteracaoAuxUpdateDTO;
import com.br.pruma.core.domain.LogAlteracao;
import com.br.pruma.core.domain.LogAlteracaoAux;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LogAlteracaoAuxMapper {

    /**
     * Converte o DTO em entidade, preenchendo:
     *  - log: stub criado a partir do logId (para @MapsId)
     *  - tipoAlteracao: mapeamento explícito do body
     */
    @Mapping(target = "log", source = "logId", qualifiedByName = "mapLogById")
    @Mapping(target = "tipoAlteracao", source = "tipoAlteracao")
    LogAlteracaoAux toEntity(LogAlteracaoAuxRequestDTO dto);

    /**
     * Atualiza somente o tipoAlteracao em uma entidade já existente a partir do UpdateDTO.
     * Mantém intactos o relacionamento log e o ID.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "log", ignore = true)
    @Mapping(target = "logId", ignore = true)
    void updateEntity(LogAlteracaoAuxUpdateDTO dto, @MappingTarget LogAlteracaoAux entity);

    /**
     * Atualiza somente o body (tipoAlteracao) em uma entidade já existente a partir do RequestDTO.
     * Mantém intactos os relacionamentos e o ID.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "log", ignore = true)
    @Mapping(target = "logId", ignore = true)
    void updateFromDto(LogAlteracaoAuxRequestDTO dto, @MappingTarget LogAlteracaoAux entity);

    /**
     * Converte entidade em DTO de resposta, extraindo:
     *  - id do log a partir de log.id
     *  - tipoAlteracao diretamente da entidade
     */
    @Mapping(target = "id", source = "log.id")
    @Mapping(target = "tipoAlteracao", source = "tipoAlteracao")
    LogAlteracaoAuxResponseDTO toResponse(LogAlteracaoAux entity);

    /**
     * Cria um stub de LogAlteracao usando o builder (acessa construtor privado).
     */
    @Named("mapLogById")
    default LogAlteracao mapLogById(Integer logId) {
        if (logId == null) {
            return null;
        }
        return LogAlteracao.builder()
                .id(logId)
                .build();
    }
}
