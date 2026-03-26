package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.core.domain.Auditoria;
import org.mapstruct.*;

@Mapper(
        componentModel                   = "spring",
        unmappedTargetPolicy             = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuditoriaMapper {

    // AuditoriaRequestDTO nao tem tipoUsuario - campos: entidade, acao, usuario, dataHora
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "version",     ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "clienteCpf",  source = "usuario")
    Auditoria toEntity(AuditoriaRequestDTO dto);

    // AuditoriaResponseDTO.tipoUsuario e String (descricao do tipo)
    @Mapping(target = "tipoUsuario", source = "tipoUsuario.descricao")
    @Mapping(target = "clienteCpf",  source = "clienteCpf")
    AuditoriaResponseDTO toResponseDTO(Auditoria auditoria);
}
