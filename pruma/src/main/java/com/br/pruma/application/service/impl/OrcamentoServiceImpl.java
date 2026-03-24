package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.service.OrcamentoService;
import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.OrcamentoRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrcamentoServiceImpl implements OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final ProjetoRepository projetoRepository;

    @Override
    @Transactional
    public OrcamentoResponseDTO criar(OrcamentoRequestDTO request) {
        Projeto projeto = projetoRepository.findById(request.projetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + request.projetoId()));
        Orcamento entity = Orcamento.builder()
                .projeto(projeto)
                .valorTotal(request.valorTotal())
                .status(request.status())
                .build();
        return toResponse(orcamentoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listarTodos() {
        return orcamentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrcamentoResponseDTO buscarPorId(Integer id) {
        return orcamentoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Orcamento não encontrado: " + id));
    }

    @Override
    @Transactional
    public OrcamentoResponseDTO atualizar(Integer id, OrcamentoRequestDTO request) {
        Orcamento entity = orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orcamento não encontrado: " + id));
        Projeto projeto = projetoRepository.findById(request.projetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + request.projetoId()));
        entity.setProjeto(projeto);
        entity.setValorTotal(request.valorTotal());
        entity.setStatus(request.status());
        return toResponse(orcamentoRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!orcamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Orcamento não encontrado: " + id);
        }
        orcamentoRepository.deleteById(id);
    }

    private OrcamentoResponseDTO toResponse(Orcamento e) {
        return new OrcamentoResponseDTO(
                e.getId(),
                e.getProjeto() != null ? e.getProjeto().getId() : null,
                e.getValorTotal(),
                e.getStatus(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
