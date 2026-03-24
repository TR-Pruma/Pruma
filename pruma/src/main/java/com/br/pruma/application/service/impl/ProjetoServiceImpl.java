package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ProjetoRequestDTO;
import com.br.pruma.application.dto.response.ProjetoResponseDTO;
import com.br.pruma.application.service.ProjetoService;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoServiceImpl implements ProjetoService {

    private final ProjetoRepository projetoRepository;

    @Override
    @Transactional
    public ProjetoResponseDTO criar(ProjetoRequestDTO request) {
        Projeto entity = Projeto.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .status(request.status())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .build();
        return toResponse(projetoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjetoResponseDTO> listarTodos() {
        return projetoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjetoResponseDTO buscarPorId(Integer id) {
        return projetoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));
    }

    @Override
    @Transactional
    public ProjetoResponseDTO atualizar(Integer id, ProjetoRequestDTO request) {
        Projeto entity = projetoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));
        entity.setNome(request.nome());
        entity.setDescricao(request.descricao());
        entity.setStatus(request.status());
        entity.setDataInicio(request.dataInicio());
        entity.setDataFim(request.dataFim());
        return toResponse(projetoRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!projetoRepository.existsById(id)) {
            throw new EntityNotFoundException("Projeto não encontrado: " + id);
        }
        projetoRepository.deleteById(id);
    }

    private ProjetoResponseDTO toResponse(Projeto e) {
        return new ProjetoResponseDTO(
                e.getId(),
                e.getNome(),
                e.getDescricao(),
                e.getStatus(),
                e.getDataInicio(),
                e.getDataFim(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
