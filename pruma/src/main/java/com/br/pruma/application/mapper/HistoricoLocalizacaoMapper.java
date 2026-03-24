package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.HistoricoLocalizacaoRequestDTO;
import com.br.pruma.application.dto.response.HistoricoLocalizacaoResponseDTO;
import com.br.pruma.core.domain.HistoricoLocalizacao;
import com.br.pruma.core.domain.ProfissionalDeBase;
import com.br.pruma.core.domain.Projeto;
import org.springframework.stereotype.Component;

@Component
public class HistoricoLocalizacaoMapper {
    public HistoricoLocalizacao toEntity(HistoricoLocalizacaoRequestDTO dto,
                                         ProfissionalDeBase profissional,
                                         Projeto projeto) {
        return HistoricoLocalizacao.builder()
                .profissional(profissional)
                .projeto(projeto)
                .localizacao(dto.localizacao())
                .dataHora(dto.dataHora())
                .build();
    }
    public HistoricoLocalizacaoResponseDTO toDTO(HistoricoLocalizacao entity) {
        return new HistoricoLocalizacaoResponseDTO(
                entity.getId(),
                entity.getProfissional().getVersion(),
                entity.getProjeto().getId(),
                entity.getLocalizacao(),
                entity.getDataHora()
        );
    }

}
