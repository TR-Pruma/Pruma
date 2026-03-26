package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.MensagemInstantaneaAuxRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaAuxResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaAuxUpdateDTO;
import com.br.pruma.core.domain.MensagemInstantanea;
import com.br.pruma.core.domain.MensagemInstantaneaAux;
import org.mapstruct.*;

@Mapper(
        componentModel                   = "spring",
        unmappedTargetPolicy             = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MensagemInstantaneaAuxMapper {

    @Mapping(target = "mensagem",   source = "mensagemId", qualifiedByName = "mapMensagemById")
    @Mapping(target = "version",    ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    @Mapping(target = "ativo",      ignore = true)
    MensagemInstantaneaAux toEntity(MensagemInstantaneaAuxRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "tipoMensagem", source = "tipoMensagem")
    @Mapping(target = "version",      ignore = true)
    @Mapping(target = "createdAt",    ignore = true)
    @Mapping(target = "updatedAt",    ignore = true)
    @Mapping(target = "ativo",        ignore = true)
    void updateFromDto(MensagemInstantaneaAuxUpdateDTO dto,
                       @MappingTarget MensagemInstantaneaAux entity);

    @Mapping(target = "mensagemId",  source = "mensagem.id")
    @Mapping(target = "tipoMensagem",source = "tipoMensagem")
    @Mapping(target = "version",     source = "version")
    @Mapping(target = "createdAt",   source = "createdAt")
    @Mapping(target = "updatedAt",   source = "updatedAt")
    MensagemInstantaneaAuxResponseDTO toResponse(MensagemInstantaneaAux entity);

    @Named("mapMensagemById")
    default MensagemInstantanea mapMensagemById(Integer mensagemId) {
        return mensagemId == null ? null : MensagemInstantanea.builder().id(mensagemId).build();
    }
}
