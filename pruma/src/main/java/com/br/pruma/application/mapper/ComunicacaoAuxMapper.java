package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ComunicacaoAuxRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoAuxResponseDTO;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.domain.ComunicacaoAux;
import org.springframework.stereotype.Component;

@Component
public class ComunicacaoAuxMapper {

    public ComunicacaoAux toEntity(ComunicacaoAuxRequestDTO dto, Comunicacao comunicacao) {
        return ComunicacaoAux.builder()
                .comunicacao(comunicacao)
                .tipoMensagem(dto.getTipoMensagem())
                .ativo(true)
                .build();
    }

    public ComunicacaoAuxResponseDTO toDTO(ComunicacaoAux entity) {
        Comunicacao comunicacao = entity.getComunicacao();

        return ComunicacaoAuxResponseDTO.builder()
                .comunicacaoId(comunicacao.getId())
                .tipoMensagem(entity.getTipoMensagem())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .versao(entity.getVersao())
                .ativo(entity.getAtivo())
                .mensagem(comunicacao.getMensagem())
                .tipoRemetente(comunicacao.getTipoRemetente())
                .projetoId(comunicacao.getProjeto().getId())
                .projetoNome(comunicacao.getProjeto().getNome())
                .clienteId(comunicacao.getCliente().getId())
                .clienteNome(comunicacao.getCliente().getNome())
                .build();
    }
}
