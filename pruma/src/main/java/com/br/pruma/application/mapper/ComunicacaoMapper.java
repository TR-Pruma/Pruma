package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ComunicacaoRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoResponseDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.domain.Projeto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComunicacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", source = "projeto")
    @Mapping(target = "cliente", source = "cliente")
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "versao", ignore = true)
    Comunicacao toEntity(ComunicacaoRequestDTO dto, Projeto projeto, Cliente cliente);

    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "projetoNome", source = "projeto.nome")
    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "clienteNome", source = "cliente.nome")
    ComunicacaoResponseDTO toDTO(Comunicacao entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Comunicacao entity, ComunicacaoRequestDTO dto);
}
