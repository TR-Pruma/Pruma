package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.core.domain.Auditoria;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.*;

@Mapper(
        componentModel                   = "spring",
        unmappedTargetPolicy             = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuditoriaMapper {

    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "version", ignore = true)
    Auditoria toEntity(AuditoriaRequestDTO dto);

    @Mapping(target = "id",          source = "id")
    @Mapping(target = "acao",        source = "acao")
    @Mapping(target = "dataHora",    source = "dataHora")
    @Mapping(target = "version",     source = "version")
    @Mapping(target = "tipoUsuario", source = "tipoUsuario", qualifiedByName = "tipoUsuarioToName")
    AuditoriaResponseDTO toResponseDTO(Auditoria auditoria);

    @Named("tipoUsuarioToName")
    default String tipoUsuarioToName(TipoUsuario tipo) {
        return tipo == null ? null : tipo.name();
    }
}
