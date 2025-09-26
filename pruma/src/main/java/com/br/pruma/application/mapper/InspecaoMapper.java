package com.br.pruma.application.mapper;
import com.br.pruma.application.dto.request.InspecaoRequestDTO;
import com.br.pruma.application.dto.response.InspecaoResponseDTO;
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
    // ====================
    // DTO → ENTIDADE (novo)
    // ====================
    public interface InspecaoMapper {
        @Mapping(target = "tecnico", source = "tecnicoCpf", qualifiedByName = "profissionalFromCpf")
        Inspecao toEntity(InspecaoRequestDTO dto);


    // ====================
    // ENTIDADE → DTO (resposta)
    // ====================
    @Mapping(source = "id",            target = "id")
    @Mapping(source = "projeto.id",    target = "projetoId")
    @Mapping(source = "tecnico.id",    target = "tecnicoCpf")
    @Mapping(source = "descricao",     target = "descricao")
    @Mapping(source = "dataInspecao",  target = "dataInspecao")
    @Mapping(source = "resultado",     target = "resultado")
    InspecaoResponseDTO toDTO(Inspecao entity);

    // ====================
    // Atualização parcial
    // ====================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "projeto", source = "projetoId", qualifiedByName = "projetoFromId")
    @Mapping(target = "tecnico", source = "tecnicoCpf", qualifiedByName = "profissionalFromCpf")
    void updateEntity(InspecaoRequestDTO dto, @MappingTarget Inspecao entity);

    // ====================
    // Converter lista
    // ====================
    List<InspecaoResponseDTO> toDTOList(List<Inspecao> entidades);

    // =========================================================================
    // Métodos auxiliares para criar instâncias vazias de Projeto e Profissional
    // =========================================================================
    @Named("projetoFromId")
    default Projeto projetoFromId(Integer projetoId) {
        if (projetoId == null) {
            return null;
        }
        // usa o builder público em vez de chamar o construtor protegido
        return Projeto
                .builder()
                .id(projetoId)
                .build();
    }


    @Named("profissionalFromCpf")
    default ProfissionalDeBase profissionalFromCpf(Long tecnicoCpf) {
        if (tecnicoCpf == null) {
            return null;
        }
        // usa o builder público em vez do construtor protegido
        return ProfissionalDeBase
                .builder()
                .id(tecnicoCpf.intValue())    // ajusta tipo conforme seu domínio
                .build();
    }

}