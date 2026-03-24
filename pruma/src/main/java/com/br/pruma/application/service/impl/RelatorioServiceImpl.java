package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.RelatorioRequestDTO;
import com.br.pruma.application.dto.response.RelatorioResponseDTO;
import com.br.pruma.application.service.RelatorioService;
import com.br.pruma.core.domain.Relatorio;
import com.br.pruma.core.repository.RelatorioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioServiceImpl implements RelatorioService {

    private final RelatorioRepository relatorioRepository;

    @Override
    @Transactional
    public RelatorioResponseDTO criar(RelatorioRequestDTO request) {
        Relatorio entity = Relatorio.builder()
                .titulo(request.titulo())
                .conteudo(request.conteudo())
                .tipo(request.tipo())
                .build();
        return toResponse(relatorioRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelatorioResponseDTO> listarTodos() {
        return relatorioRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RelatorioResponseDTO buscarPorId(Integer id) {
        return relatorioRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Relatorio não encontrado: " + id));
    }

    @Override
    @Transactional
    public RelatorioResponseDTO atualizar(Integer id, RelatorioRequestDTO request) {
        Relatorio entity = relatorioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relatorio não encontrado: " + id));
        entity.setTitulo(request.titulo());
        entity.setConteudo(request.conteudo());
        entity.setTipo(request.tipo());
        return toResponse(relatorioRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!relatorioRepository.existsById(id)) {
            throw new EntityNotFoundException("Relatorio não encontrado: " + id);
        }
        relatorioRepository.deleteById(id);
    }

    private RelatorioResponseDTO toResponse(Relatorio e) {
        return new RelatorioResponseDTO(
                e.getId(),
                e.getTitulo(),
                e.getConteudo(),
                e.getTipo(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
