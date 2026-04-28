package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.FeedbackRequestDTO;
import com.br.pruma.application.dto.response.FeedbackResponseDTO;
import com.br.pruma.application.dto.update.FeedbackUpdateDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Feedback;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FeedbackMapper {

    @Mapping(target = "projeto",     source = "projetoId",     qualifiedByName = "projetoFromId")
    @Mapping(target = "cliente",     source = "clienteCpf",    qualifiedByName = "clienteFromCpf")
    @Mapping(target = "tipoUsuario", source = "tipoUsuarioId", qualifiedByName = "tipoUsuarioFromId")
    Feedback toEntity(FeedbackRequestDTO dto);

    @Mapping(target = "projetoId",     source = "projeto.id")
    @Mapping(target = "clienteCpf",    source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId", source = "tipoUsuario.id")
    FeedbackResponseDTO toResponseDTO(Feedback feedback);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "projeto",     ignore = true)
    @Mapping(target = "cliente",     ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    void updateFromDto(FeedbackUpdateDTO dto, @MappingTarget Feedback entity);

    @Named("projetoFromId")
    default Projeto projetoFromId(Integer id) {
        return id == null ? null : Projeto.builder().id(id).build();
    }

    @Named("clienteFromCpf")
    default Cliente clienteFromCpf(String cpf) {
        return cpf == null ? null : Cliente.builder().cpf(cpf).build();
    }

    @Named("tipoUsuarioFromId")
    default TipoUsuario tipoUsuarioFromId(Integer id) {
        return id == null ? null : TipoUsuario.builder().id(id).build();
    }
}
