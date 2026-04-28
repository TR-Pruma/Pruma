package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.application.dto.update.AuditoriaUpdateDTO;
import com.br.pruma.core.domain.Auditoria;
import org.mapstruct.*;

@Mapper(
        componentModel                   = "spring",
        unmappedTargetPolicy             = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuditoriaMapper {

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "clienteCpf",  source = "usuario")
    Auditoria toEntity(AuditoriaRequestDTO dto);

    @Mapping(target = "tipoUsuario", source = "tipoUsuario.descricao")
    @Mapping(target = "clienteCpf",  source = "clienteCpf")
    AuditoriaResponseDTO toResponse(Auditoria auditoria);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "clienteCpf",  source = "usuario")
    void updateFromDto(AuditoriaUpdateDTO dto, @MappingTarget Auditoria entity);
}
