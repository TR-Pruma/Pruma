package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MensagemInstantaneaAuxRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaAuxResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaAuxUpdateDTO;
import com.br.pruma.application.mapper.MensagemInstantaneaAuxMapper;
import com.br.pruma.core.domain.MensagemInstantanea;
import com.br.pruma.core.domain.MensagemInstantaneaAux;
import com.br.pruma.core.repository.MensagemInstantaneaAuxRepository;
import com.br.pruma.core.repository.MensagemInstantaneaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MensagemInstantaneaAuxService {

    private final MensagemInstantaneaAuxRepository repository;
    private final MensagemInstantaneaRepository mensagemRepository;
    private final MensagemInstantaneaAuxMapper mapper;

    public MensagemInstantaneaAuxResponseDTO create(MensagemInstantaneaAuxRequestDTO dto) {
        MensagemInstantanea mensagem = mensagemRepository.findById(dto.getMensagemId())
                .orElseThrow(() ->
                        new EntityNotFoundException("MensagemInstantanea não encontrada: " + dto.getMensagemId())
                );

        MensagemInstantaneaAux entity = mapper.toEntity(dto);
        entity.setMensagem(mensagem);

        MensagemInstantaneaAux saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public MensagemInstantaneaAuxResponseDTO getByMensagemId(Integer mensagemId) {
        MensagemInstantaneaAux entity = repository.findByMensagem_Id(mensagemId)
                .orElseThrow(() ->
                        new EntityNotFoundException("MensagemInstantaneaAux não encontrada para mensagem: " + mensagemId)
                );
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<MensagemInstantaneaAuxResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public MensagemInstantaneaAuxResponseDTO update(Integer mensagemId, MensagemInstantaneaAuxUpdateDTO dto) {
        MensagemInstantaneaAux entity = repository.findById(mensagemId)
                .orElseThrow(() ->
                        new EntityNotFoundException("MensagemInstantaneaAux não encontrada para mensagem: " + mensagemId)
                );

        mapper.updateFromDto(dto, entity);
        MensagemInstantaneaAux updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Integer mensagemId) {
        if (!repository.existsById(mensagemId)) {
            throw new EntityNotFoundException("MensagemInstantaneaAux não encontrada para mensagem: " + mensagemId);
        }
        repository.deleteById(mensagemId);
    }
}