package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.LogAlteracaoRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoResponseDTO;
import com.br.pruma.application.dto.update.LogAlteracaoUpdateDTO;
import com.br.pruma.core.domain.LogAlteracao;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LogAlteracaoMapper {

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "projeto",     ignore = true)
    @Mapping(target = "cliente",     ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "dataHora",    ignore = true)
    LogAlteracao toEntity(LogAlteracaoRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "projeto",     ignore = true)
    @Mapping(target = "cliente",     ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "dataHora",    ignore = true)
    void updateEntity(LogAlteracaoUpdateDTO dto, @MappingTarget LogAlteracao entity);

    @Mapping(target = "projetoId",     source = "projeto.id")
    @Mapping(target = "clienteCpf",    source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId", source = "tipoUsuario.id")
    LogAlteracaoResponseDTO toResponse(LogAlteracao entity);
}
