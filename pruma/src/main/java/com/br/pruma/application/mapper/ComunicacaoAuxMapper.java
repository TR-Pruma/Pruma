package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ComunicacaoAuxRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoAuxResponseDTO;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.domain.ComunicacaoAux;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComunicacaoAuxMapper {

    @Mapping(target = "comunicacao", source = "comunicacao")
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "versao", ignore = true)
    ComunicacaoAux toEntity(ComunicacaoAuxRequestDTO dto, Comunicacao comunicacao);

    @Mapping(target = "comunicacaoId", source = "comunicacao.id")
    @Mapping(target = "mensagem", source = "comunicacao.mensagem")
    @Mapping(target = "tipoRemetente", source = "comunicacao.tipoRemetente")
    @Mapping(target = "projetoId", source = "comunicacao.projeto.id")
    @Mapping(target = "projetoNome", source = "comunicacao.projeto.nome")
    @Mapping(target = "clienteId", source = "comunicacao.cliente.id")
    @Mapping(target = "clienteNome", source = "comunicacao.cliente.nome")
    ComunicacaoAuxResponseDTO toDTO(ComunicacaoAux entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ComunicacaoAux entity, ComunicacaoAuxRequestDTO dto);
}
