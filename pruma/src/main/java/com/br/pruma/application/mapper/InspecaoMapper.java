package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.InspecaoRequestDTO;
import com.br.pruma.application.dto.response.InspecaoResponseDTO;
import com.br.pruma.application.dto.update.InspecaoUpdateDTO;
import com.br.pruma.core.domain.Inspecao;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.ProfissionalDeBase;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel                   = "spring",
        unmappedTargetPolicy             = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InspecaoMapper {

    // DTO → ENTIDADE (criação)
    @Mapping(target = "projeto",  ignore = true)
    @Mapping(target = "tecnico",  source = "tecnicoCpf", qualifiedByName = "profissionalFromCpf")
    Inspecao toEntity(InspecaoRequestDTO dto);

    // ENTIDADE → DTO (resposta)
    @Mapping(source = "id",           target = "id")
    @Mapping(source = "projeto.id",   target = "projetoId")
    @Mapping(source = "tecnico.id",   target = "tecnicoCpf")
    @Mapping(source = "descricao",    target = "descricao")
    @Mapping(source = "dataInspecao", target = "dataInspecao")
    @Mapping(source = "resultado",    target = "resultado")
    InspecaoResponseDTO toResponseDTO(Inspecao entity);

    // Atualização parcial (PATCH) — apenas campos simples, sem FK
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "projeto", ignore = true)
    @Mapping(target = "tecnico", ignore = true)
    void updateFromDto(InspecaoUpdateDTO dto, @MappingTarget Inspecao entity);

    // Lista
    List<InspecaoResponseDTO> toResponseDTOList(List<Inspecao> entidades);

    @Named("projetoFromId")
    default Projeto projetoFromId(Integer projetoId) {
        return projetoId == null ? null : Projeto.builder().id(projetoId).build();
    }

    @Named("profissionalFromCpf")
    default ProfissionalDeBase profissionalFromCpf(Long tecnicoCpf) {
        return tecnicoCpf == null ? null : ProfissionalDeBase.builder().id(tecnicoCpf.intValue()).build();
    }
}
