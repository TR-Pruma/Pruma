package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ClienteTipoRequestDTO;
import com.br.pruma.application.dto.response.ClienteTipoResponseDTO;
import com.br.pruma.core.domain.ClienteTipo;
import com.br.pruma.core.domain.TipoUsuario;
import org.springframework.stereotype.Component;

@Component
public class ClienteTipoMapper {

    public ClienteTipo toEntity(ClienteTipoRequestDTO dto, TipoUsuario tipoUsuario) {
        return ClienteTipo.builder()
                .tipoUsuario(tipoUsuario)
                .descricaoCliente(dto.getDescricaoCliente())
                .ativo(true)
                .build();
    }

    public ClienteTipoResponseDTO toDTO(ClienteTipo entity) {
        return ClienteTipoResponseDTO.builder()
                .id(entity.getId())
                .tipoUsuarioId(entity.getTipoUsuario() != null ? entity.getTipoUsuario().getId() : null)
                .descricaoCliente(entity.getDescricaoCliente())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .versao(entity.getVersao())
                .ativo(entity.getAtivo())
                .build();
    }
}
