package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ObraRequestDTO;
import com.br.pruma.application.dto.response.ObraResponseDTO;
import com.br.pruma.application.service.ObraService;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.repository.ObraRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObraServiceImpl implements ObraService {

    private final ObraRepository obraRepository;

    @Override
    @Transactional
    public ObraResponseDTO criar(ObraRequestDTO request) {
        Obra entity = Obra.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .status(request.status())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .build();
        return toResponse(obraRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObraResponseDTO> listarTodos() {
        return obraRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ObraResponseDTO buscarPorId(Integer id) {
        return obraRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + id));
    }

    @Override
    @Transactional
    public ObraResponseDTO atualizar(Integer id, ObraRequestDTO request) {
        Obra entity = obraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + id));
        entity.setNome(request.nome());
        entity.setDescricao(request.descricao());
        entity.setStatus(request.status());
        entity.setDataInicio(request.dataInicio());
        entity.setDataFim(request.dataFim());
        return toResponse(obraRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!obraRepository.existsById(id)) {
            throw new EntityNotFoundException("Obra não encontrada: " + id);
        }
        obraRepository.deleteById(id);
    }

    private ObraResponseDTO toResponse(Obra e) {
        return new ObraResponseDTO(
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
