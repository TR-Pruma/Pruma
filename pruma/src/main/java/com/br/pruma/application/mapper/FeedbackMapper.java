package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.FeedbackRequestDTO;
import com.br.pruma.application.dto.response.FeedbackResponseDTO;
import com.br.pruma.core.domain.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    @Mapping(target = "projeto.id", source = "projetoId")
    @Mapping(target = "cliente.cpf", source = "clienteCpf")
    @Mapping(target = "tipoUsuario.id", source = "tipoUsuarioId")
    Feedback toEntity(FeedbackRequestDTO dto);

    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "clienteCpf", source = "cliente.cpf")
    @Mapping(target = "tipoUsuarioId", source = "tipoUsuario.id")
    FeedbackResponseDTO toResponseDTO(Feedback feedback);
}

