package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.InspecaoRequestDTO;
import com.br.pruma.application.dto.response.InspecaoResponseDTO;
import com.br.pruma.core.domain.Inspecao;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InspecaoMapper {
    InspecaoMapper INSTANCE = Mappers.getMapper(InspecaoMapper.class);

    @Mapping(source = "projetoId", target = "projeto.id")
    @Mapping(source = "tecnicoCpf", target = "tecnico.cpf")
    Inspecao toEntity(InspecaoRequestDTO dto);

    @Mapping(source = "id",           target = "id")
    @Mapping(source = "projeto.id",   target = "projetoId")
    @Mapping(source = "tecnico.cpf",  target = "tecnicoCpf")
    @Mapping(source = "descricao",    target = "descricao")
    @Mapping(source = "dataInspecao", target = "dataInspecao")
    @Mapping(source = "resultado",    target = "resultado")
    InspecaoResponseDTO toDTO(Inspecao entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "projetoId",  target = "projeto.id")
    @Mapping(source = "tecnicoCpf", target = "tecnico.cpf")
    void updateEntity(@MappingTarget Inspecao entity, InspecaoRequestDTO dto);


}
