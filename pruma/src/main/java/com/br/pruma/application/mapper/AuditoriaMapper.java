package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.application.dto.update.AuditoriaUpdateDTO;
import com.br.pruma.core.domain.Auditoria;
import com.br.pruma.core.domain.Usuario;
import org.mapstruct.*;

@Mapper(
        componentModel                   = "spring",
        unmappedTargetPolicy             = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuditoriaMapper {

    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "idToUsuario")
    @Mapping(target = "acao",       source = "acao")
    @Mapping(target = "entidade",   source = "entidade")
    @Mapping(target = "entidadeId", source = "entidadeId")
    @Mapping(target = "detalhe",    source = "detalhe")
    Auditoria toEntity(AuditoriaRequestDTO dto);

    // Usuario nao possui campo 'nome'; expoe cpf como identificador legivel
    @Mapping(target = "usuarioId",   source = "usuario.id")
    @Mapping(target = "usuarioNome", source = "usuario.cpf")
    @Mapping(target = "createdAt",   source = "createdAt")
    AuditoriaResponseDTO toResponse(Auditoria auditoria);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateFromDto(AuditoriaUpdateDTO dto, @MappingTarget Auditoria entity);

    @Named("idToUsuario")
    static Usuario idToUsuario(Integer id) {
        return Usuario.ofId(id);
    }
}
