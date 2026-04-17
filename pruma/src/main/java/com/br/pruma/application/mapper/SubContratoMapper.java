package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.SubContratoRequestDTO;
import com.br.pruma.application.dto.response.SubContratoResponseDTO;
import com.br.pruma.application.dto.update.SubContratoUpdateDTO;
import com.br.pruma.core.domain.SubContrato;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SubContratoMapper {

    @Mapping(source = "cliente.cpf",   target = "clienteCpf")
    @Mapping(source = "cliente.nome",  target = "clienteNome")
    @Mapping(source = "projeto.id",    target = "projetoId")
    @Mapping(source = "projeto.nome",  target = "projetoNome")
    SubContratoResponseDTO toDTO(SubContrato entity);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "cliente",   ignore = true)
    @Mapping(target = "projeto",   ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    SubContrato toEntity(SubContratoRequestDTO request);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "cliente",   ignore = true)
    @Mapping(target = "projeto",   ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDto(SubContratoRequestDTO request, @MappingTarget SubContrato entity);


    // Adicionar no SubContratoMapper.java
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "cliente",   ignore = true)
    @Mapping(target = "projeto",   ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDto(SubContratoUpdateDTO dto, @MappingTarget SubContrato entity);
}