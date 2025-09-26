package com.br.pruma.application.mapper;
import com.br.pruma.application.dto.request.InsumoRequestDTO;
import com.br.pruma.application.dto.response.InsumoResponseDTO;
import com.br.pruma.core.domain.Insumo;
import com.br.pruma.core.enums.UnidadeMedida;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.List;
@Mapper(
        componentModel                   = "spring",
        unmappedTargetPolicy             = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InsumoMapper {

    // DTO → ENTIDADE (criação)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "unidadeMedida", source = "unidadeMedida", qualifiedByName = "stringToUnidade")
    Insumo toEntity(InsumoRequestDTO dto);

    // ENTIDADE → DTO (resposta)
    @Mapping(target = "unidadeMedida", source = "unidadeMedida", qualifiedByName = "unidadeToString")
    InsumoResponseDTO toDto(Insumo entity);

    // ATUALIZAÇÃO PARCIAL
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "unidadeMedida", source = "unidadeMedida", qualifiedByName = "stringToUnidade")
    void updateFromDto(InsumoRequestDTO dto, @MappingTarget Insumo entity);

    List<InsumoResponseDTO> toDtoList(List<Insumo> entities);

    @Named("stringToUnidade")
    default UnidadeMedida stringToUnidade(String name) {
        if (name == null || name.isBlank()) return null;
        try {
            return UnidadeMedida.valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Arrays.stream(UnidadeMedida.values())
                    .filter(u -> u.name().equalsIgnoreCase(name.trim()) ||
                            u.toString().equalsIgnoreCase(name.trim()))
                    .findFirst()
                    .orElse(null);
        }
    }

    @Named("unidadeToString")
    default String unidadeToString(UnidadeMedida unidade) {
        return unidade == null ? null : unidade.name();
    }
}
