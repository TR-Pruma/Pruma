package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LogAlteracaoAuxRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoAuxResponseDTO;
import com.br.pruma.application.mapper.LogAlteracaoAuxMapper;
import com.br.pruma.core.domain.LogAlteracao;
import com.br.pruma.core.domain.LogAlteracaoAux;
import com.br.pruma.core.enums.TipoAlteracao;
import com.br.pruma.core.repository.LogAlteracaoAuxRepository;
import com.br.pruma.core.repository.LogAlteracaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LogAlteracaoAuxService {

    private final LogAlteracaoAuxRepository auxRepo;
    private final LogAlteracaoRepository logRepo;
    private final LogAlteracaoAuxMapper mapper;

    public LogAlteracaoAuxResponseDTO create(LogAlteracaoAuxRequestDTO dto) {
        LogAlteracao log = logRepo.findById(dto.getLogId())
                .orElseThrow(() ->
                        new EntityNotFoundException("LogAlteracao não encontrado: " + dto.getLogId())
                );
        LogAlteracaoAux entity = mapper.toEntity(dto);
        entity.setLog(log);
        LogAlteracaoAux saved = auxRepo.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public LogAlteracaoAuxResponseDTO getById(Integer id) {
        LogAlteracaoAux entity = auxRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("LogAlteracaoAux não encontrado: " + id)
                );
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<LogAlteracaoAuxResponseDTO> listAll() {
        return auxRepo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os registros auxiliares com um determinado tipo de alteração.
     * Recebe {@link TipoAlteracao} (enum) após refatoração do domain.
     */
    @Transactional(readOnly = true)
    public List<LogAlteracaoAuxResponseDTO> listByTipoAlteracao(TipoAlteracao tipoAlteracao) {
        return auxRepo.findByTipoAlteracao(tipoAlteracao).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public LogAlteracaoAuxResponseDTO update(Integer id, LogAlteracaoAuxRequestDTO dto) {
        LogAlteracaoAux entity = auxRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("LogAlteracaoAux não encontrado: " + id)
                );
        mapper.updateFromDto(dto, entity);
        LogAlteracaoAux updated = auxRepo.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Integer id) {
        if (!auxRepo.existsById(id)) {
            throw new EntityNotFoundException("LogAlteracaoAux não encontrado: " + id);
        }
        auxRepo.deleteById(id);
    }
}
