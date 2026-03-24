package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.LogradouroRequestDTO;
import com.br.pruma.application.dto.response.LogradouroResponseDTO;
import com.br.pruma.application.service.LogradouroService;
import com.br.pruma.core.domain.Logradouro;
import com.br.pruma.core.repository.LogradouroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogradouroServiceImpl implements LogradouroService {

    private final LogradouroRepository logradouroRepository;

    @Override
    @Transactional
    public LogradouroResponseDTO criar(LogradouroRequestDTO request) {
        Logradouro entity = Logradouro.builder()
                .nome(request.nome())
                .tipo(request.tipo())
                .bairro(request.bairro())
                .cidade(request.cidade())
                .estado(request.estado())
                .cep(request.cep())
                .build();
        return toResponse(logradouroRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogradouroResponseDTO> listarTodos() {
        return logradouroRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LogradouroResponseDTO buscarPorId(Integer id) {
        return logradouroRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Logradouro não encontrado: " + id));
    }

    @Override
    @Transactional
    public LogradouroResponseDTO atualizar(Integer id, LogradouroRequestDTO request) {
        Logradouro entity = logradouroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Logradouro não encontrado: " + id));
        entity.setNome(request.nome());
        entity.setTipo(request.tipo());
        entity.setBairro(request.bairro());
        entity.setCidade(request.cidade());
        entity.setEstado(request.estado());
        entity.setCep(request.cep());
        return toResponse(logradouroRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!logradouroRepository.existsById(id)) {
            throw new EntityNotFoundException("Logradouro não encontrado: " + id);
        }
        logradouroRepository.deleteById(id);
    }

    private LogradouroResponseDTO toResponse(Logradouro e) {
        return new LogradouroResponseDTO(
                e.getId(),
                e.getNome(),
                e.getTipo(),
                e.getBairro(),
                e.getCidade(),
                e.getEstado(),
                e.getCep(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
