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

    // Ignora id e versão (preenchidos pelo JPA)
    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "version", ignore = true)
    Auditoria toEntity(AuditoriaRequestDTO dto);

    @Mapping(target = "id",       source = "id")
    @Mapping(target = "acao",     source = "acao")
    @Mapping(target = "dataHora", source = "dataHora")
    @Mapping(target = "version",  source = "version")
    // mapeia a String do DTO diretamente de tipoUsuario.getDescricao()
    @Mapping(target = "tipoUsuario", source = "tipoUsuario.descricao")
    AuditoriaResponseDTO toResponseDTO(Auditoria auditoria);

}

