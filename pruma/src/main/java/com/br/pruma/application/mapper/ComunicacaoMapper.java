package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ComunicacaoRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoResponseDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.domain.Projeto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ComunicacaoMapper {

    public Comunicacao toEntity(ComunicacaoRequestDTO dto, Cliente cliente, Projeto projeto) {
        return Comunicacao.builder()
                .cliente(cliente)
                .projeto(projeto)
                .mensagem(dto.getMensagem())
                .tipoRemetente(dto.getTipoRemetente())
                .dataHora(LocalDateTime.now())
                .ativo(true)
                .build();
    }

    public ComunicacaoResponseDTO toResponse(Comunicacao entity) {
        return ComunicacaoResponseDTO.builder()
                .id(entity.getId())
                .mensagem(entity.getMensagem())
                .tipoRemetente(entity.getTipoRemetente())
                .projetoId(entity.getProjeto().getId())
                .projetoNome(entity.getProjeto().getNome())
                .clienteId(entity.getCliente().getId())
                .clienteNome(entity.getCliente().getNome())
                .dataHora(entity.getDataHora())
                .dataAtualizacao(entity.getDataAtualizacao())
                .versao(entity.getVersao())
                .ativo(entity.getAtivo())
                .build();
    }

    public void updateEntity(Comunicacao entity, ComunicacaoRequestDTO dto, Cliente cliente, Projeto projeto) {
        entity.setCliente(cliente);
        entity.setProjeto(projeto);
        entity.setMensagem(dto.getMensagem());
        entity.setTipoRemetente(dto.getTipoRemetente());
    }
}
