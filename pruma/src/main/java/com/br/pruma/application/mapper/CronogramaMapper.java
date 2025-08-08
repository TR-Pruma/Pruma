package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.core.domain.Cronograma;
import com.br.pruma.core.domain.Projeto;
import org.springframework.stereotype.Component;

@Component
public class CronogramaMapper {

    public Cronograma toEntity(CronogramaRequestDTO dto, Projeto projeto) {
        return Cronograma.builder()
                .projeto(projeto)
                .dataInicio(dto.getDataInicio())
                .dataFim(dto.getDataFim())
                .descricao(dto.getDescricao())
                .ativo(true)
                .build();
    }
    public CronogramaResponseDTO toResponse(Cronograma entity) {
        return CronogramaResponseDTO.builder()
                .id(entity.getId())
                .projetoId(entity.getProjeto().getId())
                .projetoNome(entity.getProjeto().getNome())
                .dataInicio(entity.getDataInicio())
                .dataFim(entity.getDataFim())
                .descricao(entity.getDescricao())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .versao(entity.getVersao())
                .ativo(entity.getAtivo())
                .build();
    }

    public void updateEntity(Cronograma entity, CronogramaRequestDTO dto, Projeto projeto) {
        entity.setProjeto(projeto);
        entity.setDataInicio(dto.getDataInicio());
        entity.setDataFim(dto.getDataFim());
        entity.setDescricao(dto.getDescricao());
    }
}

