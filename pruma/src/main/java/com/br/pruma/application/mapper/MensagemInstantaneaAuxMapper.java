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

    // ====== DTO → Entidade (create) ======
    @Mapping(target = "mensagem",       source = "mensagemId", qualifiedByName = "mapMensagemById")
    @Mapping(target = "version",        ignore = true)
    @Mapping(target = "createdAt",      ignore = true)
    @Mapping(target = "updatedAt",      ignore = true)
    MensagemInstantaneaAux toEntity(MensagemInstantaneaAuxRequestDTO dto);

    // ====== DTO → Entidade (update parcial) ======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // removemos o mapeamento de mensagemId porque o DTO de update não o possui
    @Mapping(target = "tipoMensagem",   source = "tipoMensagem")
    @Mapping(target = "version",        ignore = true)
    @Mapping(target = "createdAt",      ignore = true)
    @Mapping(target = "updatedAt",      ignore = true)
    void updateFromDto(MensagemInstantaneaAuxUpdateDTO dto,
                       @MappingTarget MensagemInstantaneaAux entity);

    // ====== Entidade → DTO (response) ======
    @Mapping(target = "mensagemId",       source = "mensagem.id")
    @Mapping(target = "tipoMensagem",     source = "tipoMensagem")
    @Mapping(target = "version",          source = "version")
    @Mapping(target = "createdAt",        source = "createdAt")
    @Mapping(target = "updatedAt",        source = "updatedAt")
    MensagemInstantaneaAuxResponseDTO toResponse(MensagemInstantaneaAux entity);

    // ====== Conversor auxiliar para @MapsId ======
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