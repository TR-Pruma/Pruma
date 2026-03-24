package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.HistoricoLocalizacaoRequestDTO;
import com.br.pruma.application.dto.response.HistoricoLocalizacaoResponseDTO;
import com.br.pruma.application.service.HistoricoLocalizacaoService;
import com.br.pruma.core.domain.HistoricoLocalizacao;
import com.br.pruma.core.repository.HistoricoLocalizacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoLocalizacaoServiceImpl implements HistoricoLocalizacaoService {

    private final HistoricoLocalizacaoRepository historicoLocalizacaoRepository;

    @Override
    @Transactional
    public HistoricoLocalizacaoResponseDTO criar(HistoricoLocalizacaoRequestDTO request) {
        HistoricoLocalizacao entity = HistoricoLocalizacao.builder()
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build();
        return toResponse(historicoLocalizacaoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoricoLocalizacaoResponseDTO> listarTodos() {
        return historicoLocalizacaoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HistoricoLocalizacaoResponseDTO buscarPorId(Integer id) {
        return historicoLocalizacaoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("HistoricoLocalizacao não encontrado: " + id));
    }

    @Override
    @Transactional
    public HistoricoLocalizacaoResponseDTO atualizar(Integer id, HistoricoLocalizacaoRequestDTO request) {
        HistoricoLocalizacao entity = historicoLocalizacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HistoricoLocalizacao não encontrado: " + id));
        entity.setLatitude(request.latitude());
        entity.setLongitude(request.longitude());
        return toResponse(historicoLocalizacaoRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!historicoLocalizacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("HistoricoLocalizacao não encontrado: " + id);
        }
        historicoLocalizacaoRepository.deleteById(id);
    }

    private HistoricoLocalizacaoResponseDTO toResponse(HistoricoLocalizacao e) {
        return new HistoricoLocalizacaoResponseDTO(
                e.getId(),
                e.getLatitude(),
                e.getLongitude(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
