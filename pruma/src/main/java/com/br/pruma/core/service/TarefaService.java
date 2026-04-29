package com.br.pruma.core.service;

import com.br.pruma.application.dto.request.TarefaRequestDTO;
import com.br.pruma.application.dto.response.TarefaResponseDTO;
import com.br.pruma.application.dto.update.TarefaUpdateDTO;
import com.br.pruma.application.mapper.TarefaMapper;
import com.br.pruma.core.domain.Tarefa;
import com.br.pruma.core.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;

    @Transactional
    public TarefaResponseDTO criar(TarefaRequestDTO dto) {
        Tarefa tarefa = tarefaMapper.toEntity(dto);
        return tarefaMapper.toResponse(tarefaRepository.save(tarefa));
    }

    @Transactional(readOnly = true)
    public TarefaResponseDTO buscarPorId(Integer id) {
        return tarefaMapper.toResponse(buscarEntidadePorId(id));
    }

    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> listarTodas() {
        return tarefaMapper.toResponseList(tarefaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> listarPorAtividade(Integer atividadeId) {
        return tarefaMapper.toResponseList(tarefaRepository.findByAtividadeId(atividadeId));
    }

    @Transactional
    public TarefaResponseDTO atualizar(Integer id, TarefaUpdateDTO dto) {
        Tarefa tarefa = buscarEntidadePorId(id);
        tarefaMapper.updateFromDto(dto, tarefa);
        return tarefaMapper.toResponse(tarefaRepository.save(tarefa));
    }

    @Transactional
    public void deletar(Integer id) {
        if (!tarefaRepository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não encontrada com id: " + id);
        }
        tarefaRepository.deleteById(id);
    }

    private Tarefa buscarEntidadePorId(Integer id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));
    }
}
