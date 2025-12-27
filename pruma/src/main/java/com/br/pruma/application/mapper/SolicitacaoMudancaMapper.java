package com.br.pruma.application.mapper;


import com.br.pruma.application.dto.request.SolicitacaoMudancaRequestDTO;
import com.br.pruma.application.dto.response.SolicitacaoMudancaResponseDTO;
import com.br.pruma.core.domain.SolicitacaoMudanca;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SolicitacaoMudancaMapper {

    @Mapping(source = "projeto.id", target = "projetoId")
    @Mapping(source = "projeto.nome", target = "projetoNome")
    @Mapping(source = "statusSolicitacao.id", target = "statusSolicitacaoId")
    @Mapping(source = "statusSolicitacao.descricao", target = "statusSolicitacaoDescricao")
    SolicitacaoMudancaResponseDTO toDTO(SolicitacaoMudanca entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", ignore = true)
    @Mapping(target = "statusSolicitacao", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    SolicitacaoMudanca toEntity(SolicitacaoMudancaRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", ignore = true)
    @Mapping(target = "statusSolicitacao", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDto(SolicitacaoMudancaRequestDTO request, @MappingTarget SolicitacaoMudanca entity);
}