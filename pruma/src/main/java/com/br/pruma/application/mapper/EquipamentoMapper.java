package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoAtivoUpdateDTO;
import com.br.pruma.application.dto.response.EquipamentoListDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.dto.response.EquipamentoStatusUpdateDTO;
import com.br.pruma.core.domain.Equipamento;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipamentoMapper {
    // ====== DTO → Entidade ======

    @Mapping(target = "id", ignore = true) // no create, o ID é gerado pelo banco
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    Equipamento toEntity(EquipamentoRequestDTO dto);

    // Atualização parcial → atualiza apenas campos não nulos
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EquipamentoRequestDTO dto, @MappingTarget Equipamento entity);

    // ====== Entidade → DTO ======

    EquipamentoResponseDTO toResponseDto(Equipamento entity);

    EquipamentoListDTO toListDto(Equipamento entity);

    List<EquipamentoListDTO> toListDtoList(List<Equipamento> entities);

    // ====== Atualizações simples ======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStatusFromDto(EquipamentoStatusUpdateDTO dto, @MappingTarget Equipamento entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAtivoFromDto(EquipamentoAtivoUpdateDTO dto, @MappingTarget Equipamento entity);
}

