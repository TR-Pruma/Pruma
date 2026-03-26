package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.StatusSolicitacaoRequestDTO;
import com.br.pruma.application.dto.response.StatusSolicitacaoResponseDTO;
import com.br.pruma.application.dto.update.StatusSolicitacaoUpdateDTO;
import com.br.pruma.core.domain.StatusSolicitacao;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface StatusSolicitacaoMapper {

    StatusSolicitacao toEntity(StatusSolicitacaoRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(StatusSolicitacaoUpdateDTO dto, @MappingTarget StatusSolicitacao entity);

    StatusSolicitacaoResponseDTO toResponse(StatusSolicitacao entity);
}
