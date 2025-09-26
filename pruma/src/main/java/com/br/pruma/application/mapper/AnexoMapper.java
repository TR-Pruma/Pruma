package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AnexoRequestDTO;
import com.br.pruma.application.dto.response.AnexoResponseDTO;
import com.br.pruma.core.domain.Anexo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.br.pruma.application.dto.request.AnexoRequestDTO;
import com.br.pruma.application.dto.response.AnexoResponseDTO;
import com.br.pruma.core.domain.Anexo;
import com.br.pruma.core.domain.Projeto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AnexoMapper {

    // ====== DTO → Entidade (create) ======
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", source = "projetoId", qualifiedByName = "mapProjetoById")
    @Mapping(target = "nomeArquivo", ignore = true)
    @Mapping(target = "contentType", ignore = true)
    @Mapping(target = "tamanhoBytes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Anexo toEntity(AnexoRequestDTO dto);

    // ====== Entidade → DTO (response) ======
    @Mapping(target = "id", source = "id")
    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "tipoAnexo", source = "tipoAnexo")
    @Mapping(target = "caminhoArquivo", source = "caminhoArquivo")
    AnexoResponseDTO toResponseDTO(Anexo entity);

    // ====== Conversor auxiliar para projetoId → Projeto ======
    @Named("mapProjetoById")
    default Projeto mapProjetoById(Integer projetoId) {
        if (projetoId == null) return null;
        return Projeto.builder().id(projetoId).build();
    }
}

