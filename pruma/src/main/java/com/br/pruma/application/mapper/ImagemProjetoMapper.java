package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ImagemProjetoRequestDTO;
import com.br.pruma.application.dto.response.ImagemProjetoResponseDTO;
import com.br.pruma.application.dto.update.ImagemProjetoUpdateDTO;
import com.br.pruma.core.domain.ImagemProjeto;
import com.br.pruma.core.domain.Projeto;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ImagemProjetoMapper {

    @Mapping(source = "projeto.id", target = "projetoId")
    ImagemProjetoResponseDTO toResponseDTO(ImagemProjeto entity);

    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "projeto", source = "projetoId", qualifiedByName = "projetoFromId")
    ImagemProjeto toEntity(ImagemProjetoRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "projeto", ignore = true)
    void updateFromDto(ImagemProjetoUpdateDTO dto, @MappingTarget ImagemProjeto entity);

    @Named("projetoFromId")
    default Projeto projetoFromId(Integer id) {
        return Projeto.ofId(id);
    }
}
