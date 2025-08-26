package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.InspecaoRequestDTO;
import com.br.pruma.application.dto.response.InspecaoResponseDTO;
import com.br.pruma.application.mapper.InspecaoMapper;
import com.br.pruma.core.domain.Inspecao;
import com.br.pruma.core.repository.InspecaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspecaoService {

    private final InspecaoRepository repository;
    private final InspecaoMapper mapper;

    public List<InspecaoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public InspecaoResponseDTO buscarPorId(Integer id) {
        Inspecao entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Inspeção não encontrada com id " + id
                ));
        return mapper.toDTO(entity);
    }
    public InspecaoResponseDTO criar(InspecaoRequestDTO dto) {
        Inspecao nova = mapper.toEntity(dto);
        Inspecao salva = repository.save(nova);
        return mapper.toDTO(salva);
    }
    public InspecaoResponseDTO atualizar(Integer id, InspecaoRequestDTO dto) {
        Inspecao existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Inspeção não encontrada com id " + id
                ));
        mapper.updateEntity(existente, dto);
        Inspecao atualizada = repository.save(existente);
        return mapper.toDTO(atualizada);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Inspeção não encontrada com id " + id
            );
        }
        repository.deleteById(id);
    }


}
