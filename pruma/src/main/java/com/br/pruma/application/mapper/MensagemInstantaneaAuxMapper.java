package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.MensagemInstantaneaAuxRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaAuxResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaAuxUpdateDTO;
import com.br.pruma.core.domain.MensagemInstantanea;
import com.br.pruma.core.domain.MensagemInstantaneaAux;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MensagemInstantaneaAuxMapper {

    /**
     * Converte o DTO de criação em entidade auxiliar.
     * Ignora campos gerenciados pelo JPA/auditoria e associa stub para MensagemInstantanea.
     */
    @Mapping(target = "mensagemId", ignore = true)
    @Mapping(target = "version",    ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    @Mapping(target = "mensagem",   source = "mensagemId", qualifiedByName = "mapMensagemById")
    @Mapping(target = "tipoMensagem", source = "tipoMensagem")
    MensagemInstantaneaAux toEntity(MensagemInstantaneaAuxRequestDTO dto);

    /**
     * Atualiza somente campo tipoMensagem se presente.
     * Preserva PK, versão e timestamps.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "mensagemId", ignore = true)
    @Mapping(target = "version",    ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    @Mapping(target = "mensagem",   source = "mensagemId", qualifiedByName = "mapMensagemById")
    @Mapping(target = "tipoMensagem", source = "tipoMensagem")
    void updateFromDto(MensagemInstantaneaAuxUpdateDTO dto, @MappingTarget MensagemInstantaneaAux entity);

    /**
     * Converte entidade auxiliar em DTO de resposta.
     */
    @Mapping(target = "mensagemId", source = "mensagemId")
    @Mapping(target = "tipoMensagem", source = "tipoMensagem")
    @Mapping(target = "version",      source = "version")
    @Mapping(target = "createdAt",    source = "createdAt")
    @Mapping(target = "updatedAt",    source = "updatedAt")
    MensagemInstantaneaAuxResponseDTO toResponse(MensagemInstantaneaAux entity);

    /**
     * Stub de MensagemInstantanea para @MapsId.
     */
    @Named("mapMensagemById")
    default MensagemInstantanea mapMensagemById(Integer mensagemId) {
        if (mensagemId == null) {
            return null;
        }
        return MensagemInstantanea.builder()
                .id(mensagemId)
                .build();
    }
}