package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.dto.update.OrcamentoUpdateDTO;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.enums.StatusOrcamento;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrcamentoMapper {

    /**
     * Converte OrcamentoRequestDTO em entidade Orcamento.
     * Ignora campos de auditoria e chave primária.
     */
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "version",    ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    @Mapping(target = "projeto",    source = "projetoId",    qualifiedByName = "mapProjetoById")
    @Mapping(target = "empresa",    source = "empresaCnpj",  qualifiedByName = "mapEmpresaByCnpj")
    @Mapping(target = "valor",      source = "valor")
    @Mapping(target = "dataEnvio",  source = "dataEnvio")
    @Mapping(target = "status",     source = "status",       qualifiedByName = "mapStatusByName")
    Orcamento toEntity(OrcamentoRequestDTO dto);

    /**
     * Atualiza parcialmente uma entidade Orcamento a partir de OrcamentoUpdateDTO.
     * Campos nulos no DTO são ignorados.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "version",    ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    @Mapping(target = "projeto",    source = "projetoId",    qualifiedByName = "mapProjetoById")
    @Mapping(target = "empresa",    source = "empresaCnpj",  qualifiedByName = "mapEmpresaByCnpj")
    @Mapping(target = "valor",      source = "valor")
    @Mapping(target = "dataEnvio",  source = "dataEnvio")
    @Mapping(target = "status",     source = "status",       qualifiedByName = "mapStatusByName")
    void updateFromDto(OrcamentoUpdateDTO dto, @MappingTarget Orcamento entity);

    /**
     * Converte entidade Orcamento em OrcamentoResponseDTO.
     */
    @Mapping(target = "id",          source = "id")
    @Mapping(target = "projetoId",   source = "projeto.id")
    @Mapping(target = "empresaCnpj", source = "empresa.cnpj")
    @Mapping(target = "valor",       source = "valor")
    @Mapping(target = "dataEnvio",   source = "dataEnvio")
    @Mapping(target = "status",      source = "status",       qualifiedByName = "mapStatusToName")
    @Mapping(target = "version",     source = "version")
    @Mapping(target = "createdAt",   source = "createdAt")
    @Mapping(target = "updatedAt",   source = "updatedAt")
    OrcamentoResponseDTO toResponse(Orcamento entity);

    /**
     * Stub para associação de Projeto por ID.
     */
    @Named("mapProjetoById")
    default Projeto mapProjetoById(Integer id) {
        if (id == null) {
            return null;
        }
        return Projeto.builder()
                .id(id)
                .build();
    }

    /**
     * Stub para associação de Empresa por CNPJ.
     */
    @Named("mapEmpresaByCnpj")
    default Empresa mapEmpresaByCnpj(String cnpj) {
        if (cnpj == null) {
            return null;
        }
        return Empresa.builder()
                .cnpj(cnpj)
                .build();
    }

    /**
     * Converte String em StatusOrcamento.
     */
    @Named("mapStatusByName")
    default StatusOrcamento mapStatusByName(String status) {
        if (status == null) {
            return null;
        }
        return StatusOrcamento.valueOf(status);
    }

    /**
     * Converte StatusOrcamento em String.
     */
    @Named("mapStatusToName")
    default String mapStatusToName(StatusOrcamento status) {
        if (status == null) {
            return null;
        }
        return status.name();
    }
}