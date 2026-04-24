package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.SolicitacaoMudancaRequestDTO;
import com.br.pruma.application.dto.response.SolicitacaoMudancaResponseDTO;
import com.br.pruma.application.dto.update.SolicitacaoMudancaUpdateDTO;
import com.br.pruma.core.domain.SolicitacaoMudanca;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SolicitacaoMudancaMapper {

    // StatusSolicitacao tem 'descricaoSolicitacao', nao 'descricao'
    @Mapping(source = "projeto.id",                              target = "projetoId")
    @Mapping(source = "projeto.nome",                            target = "projetoNome")
    @Mapping(source = "statusSolicitacao.id",                    target = "statusSolicitacaoId")
    @Mapping(source = "statusSolicitacao.descricaoSolicitacao",  target = "statusSolicitacaoDescricao")
    SolicitacaoMudancaResponseDTO toDTO(SolicitacaoMudanca entity);

    @Mapping(target = "id",                 ignore = true)
    @Mapping(target = "projeto",            ignore = true)
    @Mapping(target = "statusSolicitacao",  ignore = true)
    SolicitacaoMudanca toEntity(SolicitacaoMudancaRequestDTO request);

    @Mapping(target = "id",                ignore = true)
    @Mapping(target = "projeto",           ignore = true)
    @Mapping(target = "statusSolicitacao", ignore = true)
    @Mapping(target = "createdAt",         ignore = true)
    void updateFromDto(SolicitacaoMudancaRequestDTO request, @MappingTarget SolicitacaoMudanca entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",                ignore = true)
    @Mapping(target = "projeto",           ignore = true)
    @Mapping(target = "statusSolicitacao", ignore = true)
    @Mapping(target = "createdAt",         ignore = true)
// justificativa e descricao são mapeados por nome automaticamente
    void updateFromDto(SolicitacaoMudancaUpdateDTO dto, @MappingTarget SolicitacaoMudanca entity);
}
