package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.FeedbackRequestDTO;
import com.br.pruma.application.dto.response.FeedbackResponseDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Feedback;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    @Mapping(target = "projeto",     source = "projetoId",    qualifiedByName = "projetoFromId")
    @Mapping(target = "cliente",     source = "clienteCpf",   qualifiedByName = "clienteFromCpf")
    @Mapping(target = "tipoUsuario", source = "tipoUsuarioId", qualifiedByName = "tipoUsuarioFromId")
    Feedback toEntity(FeedbackRequestDTO dto);

    @Mapping(target = "projetoId",     source = "projeto.id")
    @Mapping(target = "clienteCpf",    source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId", source = "tipoUsuario", qualifiedByName = "tipoUsuarioToName")
    FeedbackResponseDTO toResponseDTO(Feedback feedback);

    @Named("projetoFromId")
    default Projeto projetoFromId(Integer id) {
        return id == null ? null : Projeto.builder().id(id).build();
    }

    @Named("clienteFromCpf")
    default Cliente clienteFromCpf(String cpf) {
        if (cpf == null) return null;
        return Cliente.builder().cpf(cpf).build();
    }

    @Named("tipoUsuarioFromId")
    default TipoUsuario tipoUsuarioFromId(String name) {
        return name == null ? null : TipoUsuario.valueOf(name);
    }

    @Named("tipoUsuarioToName")
    default String tipoUsuarioToName(TipoUsuario tipo) {
        return tipo == null ? null : tipo.name();
    }
}
