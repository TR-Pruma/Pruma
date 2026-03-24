package com.br.pruma.application.mapper;
import com.br.pruma.application.dto.request.PosObraRequestDTO;
import com.br.pruma.application.dto.response.PosObraResponseDTO;
import com.br.pruma.application.dto.update.PosObraUpdateDTO;
import org.mapstruct.*;
import com.br.pruma.core.domain.PosObra;
import com.br.pruma.core.domain.Obra;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface PosObraMapper {

    /**
     * Converte PosObraRequestDTO em entidade PosObra.
     * Ignora campos de auditoria e chave primária.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "obra", source = "obraId", qualifiedByName = "mapObraById")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "dataConclusao", source = "dataConclusao", qualifiedByName = "localDateToDate")
    PosObra toEntity(PosObraRequestDTO dto);

    /**
     * Atualiza parcialmente uma entidade PosObra a partir de PosObraUpdateDTO.
     * Campos nulos no DTO são ignorados.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "obra", source = "obraId", qualifiedByName = "mapObraById")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "dataConclusao", source = "dataConclusao", qualifiedByName = "localDateToDate")
    void updateFromDto(PosObraUpdateDTO dto, @MappingTarget PosObra entity);

    /**
     * Converte entidade PosObra em PosObraResponseDTO.
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "obraId", source = "obra.id")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "dataConclusao", source = "dataConclusao", qualifiedByName = "dateToLocalDate")
    PosObraResponseDTO toResponse(PosObra entity);

    /**
     * Stub para associação de Obra por ID.
     */
    @Named("mapObraById")
    default Obra mapObraById(Long id) {
        if (id == null) {
            return null;
        }
        return Obra.builder()
                .id(id.intValue())
                .build();
    }

    /**
     * Converte LocalDate em java.util.Date (meio-dia system default) para persistência.
     */
    @Named("localDateToDate")
    default Date localDateToDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Converte java.util.Date em LocalDate usando system default zone.
     */
    @Named("dateToLocalDate")
    default LocalDate dateToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}