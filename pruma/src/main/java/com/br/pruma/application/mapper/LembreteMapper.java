package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.LembreteRequestDTO;
import com.br.pruma.application.dto.response.LembreteResponseDTO;
import com.br.pruma.application.dto.update.LembreteUpdateDTO;
import com.br.pruma.core.domain.Lembrete;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LembreteMapper {

    /**
     * Converte o DTO de criação em entidade Lembrete.
     * Ignora campos gerenciados pelo JPA e relacionamentos.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Lembrete toEntity(LembreteRequestDTO dto);

    /**
     * Atualiza apenas os campos não-nulos do DTO de atualização na entidade.
     * Deixa relacionamentos, versão e auditoria intactos.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(LembreteUpdateDTO dto, @MappingTarget Lembrete entity);

    /**
     * Converte a entidade em DTO de resposta, extraindo os campos das associações.
     */
    @Mapping(target = "clienteCpf", source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId", source = "tipoUsuario.id")
    LembreteResponseDTO toResponse(Lembrete entity);

}
