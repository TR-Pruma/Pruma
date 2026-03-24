package com.br.pruma.application.mapper;
import com.br.pruma.application.dto.request.ObraRequestDTO;
import com.br.pruma.application.dto.response.ObraResponseDTO;
import com.br.pruma.application.dto.update.ObraUpdateDTO;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.Projeto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ObraMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", source = "projetoId", qualifiedByName = "mapProjetoById")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "dataFim", source = "dataFim")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Obra toEntity(ObraRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", source = "projetoId", qualifiedByName = "mapProjetoById")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "dataFim", source = "dataFim")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateFromDto(ObraUpdateDTO dto, @MappingTarget Obra entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "dataInicio", source = "dataInicio")
    @Mapping(target = "dataFim", source = "dataFim")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "version", source = "version")
    ObraResponseDTO toResponse(Obra entity);

    @Named("mapProjetoById")
    default Projeto mapProjetoById(Integer id) {
        if (id == null) {
            return null;
        }
        return Projeto.builder()
                .id(id)
                .build();
    }
}
