package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.update.EquipamentoAtivoUpdateDTO;
import com.br.pruma.application.dto.update.EquipamentoStatusUpdateDTO;
import com.br.pruma.application.dto.response.EquipamentoListDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.core.domain.Equipamento;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EquipamentoMapper {

    /**
     * Cria entidade a partir do DTO de criação.
     */
    @Mapping(target = "id", ignore = true)
    Equipamento toEntity(EquipamentoRequestDTO dto);

    /**
     * Atualiza entidade existente com os campos não-nulos do DTO de criação/atualização.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(EquipamentoRequestDTO dto, @MappingTarget Equipamento entity);

    /**
     * Converte entidade para DTO de resposta detalhada.
     */
    EquipamentoResponseDTO toResponseDto(Equipamento entity);

    /**
     * Converte entidade para DTO de listagem.
     */
    EquipamentoListDTO toListDto(Equipamento entity);

    List<EquipamentoListDTO> toListDtoList(List<Equipamento> entities);

    /**
     * Atualiza apenas o campo "ativo" da entidade.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAtivoFromDto(EquipamentoAtivoUpdateDTO dto, @MappingTarget Equipamento entity);

    /**
     * Atualiza apenas o campo "status" da entidade.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStatusFromDto(EquipamentoStatusUpdateDTO dto, @MappingTarget Equipamento entity);
}



