package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.dto.update.OrcamentoUpdateDTO;
import com.br.pruma.core.domain.Orcamento;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrcamentoMapper {

    /**
     * Cria entidade a partir do DTO de criação.
     * Campos de relacionamento e de auditoria são gerenciados no service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "valor", source = "dto.valor")
    @Mapping(target = "dataEnvio", source = "dto.dataEnvio")
    Orcamento toEntity(OrcamentoRequestDTO dto);

    /**
     * Atualiza apenas campos não-nulos do DTO de atualização.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(OrcamentoUpdateDTO dto, @MappingTarget Orcamento entity);

    /**
     * Converte a entidade em DTO de resposta.
     */
    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "empresaCnpj", source = "empresa.cnpj")
    OrcamentoResponseDTO toResponse(Orcamento entity);


}
