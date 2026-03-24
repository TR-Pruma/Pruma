package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.MaterialUtilizadoRequestDTO;
import com.br.pruma.application.dto.response.MaterialUtilizadoResponseDTO;
import com.br.pruma.application.dto.update.MaterialUtilizadoUpdateDTO;
import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.domain.Material;
import com.br.pruma.core.domain.MaterialUtilizado;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MaterialUtilizadoMapper {

    /**
     * Converte o DTO de criação em entidade.
     * Ignora campos gerenciados pelo JPA/auditoria e usa stubs para FK via @MapsId.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "material", source = "materialId", qualifiedByName = "mapMaterialById")
    @Mapping(target = "atividade", source = "atividadeId", qualifiedByName = "mapAtividadeById")
    @Mapping(target = "quantidadeUtilizada", source = "quantidadeUtilizada")
    MaterialUtilizado toEntity(MaterialUtilizadoRequestDTO dto);

    /**
     * Atualiza campos não-nulos do DTO na entidade existente.
     * Mantém intactos ID, versão e timestamps de auditoria.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "material", source = "materialId", qualifiedByName = "mapMaterialById")
    @Mapping(target = "atividade", source = "atividadeId", qualifiedByName = "mapAtividadeById")
    @Mapping(target = "quantidadeUtilizada", source = "quantidadeUtilizada")
    void updateFromDto(MaterialUtilizadoUpdateDTO dto, @MappingTarget MaterialUtilizado entity);

    /**
     * Converte entidade em DTO de resposta, extraindo IDs de recurso e auditoria.
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "materialId", source = "material.id")
    @Mapping(target = "atividadeId", source = "atividade.id")
    @Mapping(target = "quantidadeUtilizada", source = "quantidadeUtilizada")
    @Mapping(target = "version", source = "version")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    MaterialUtilizadoResponseDTO toResponse(MaterialUtilizado entity);

    /**
     * Cria stub de Material para @MapsId.
     */
    @Named("mapMaterialById")
    default Material mapMaterialById(Integer materialId) {
        if (materialId == null) {
            return null;
        }
        return Material.builder()
                .id(materialId)
                .build();
    }

    /**
     * Cria stub de Atividade para @MapsId.
     */
    @Named("mapAtividadeById")
    default Atividade mapAtividadeById(Integer atividadeId) {
        if (atividadeId == null) {
            return null;
        }
        return Atividade.builder()
                .id(atividadeId)
                .build();
    }
}

