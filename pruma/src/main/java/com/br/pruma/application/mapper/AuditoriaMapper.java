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

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "version",     ignore = true)
    @Mapping(target = "dataHora",    ignore = true)
    @Mapping(target = "tipoUsuario", source = "tipoUsuarioId", qualifiedByName = "tipoUsuarioFromId")
    Auditoria toEntity(AuditoriaRequestDTO dto);

    @Mapping(target = "tipoUsuarioId", source = "tipoUsuario.id")
    AuditoriaResponseDTO toResponseDTO(Auditoria auditoria);

    @Named("tipoUsuarioFromId")
    default TipoUsuario tipoUsuarioFromId(Integer id) {
        return id == null ? null : TipoUsuario.builder().id(id).build();
    }
}
