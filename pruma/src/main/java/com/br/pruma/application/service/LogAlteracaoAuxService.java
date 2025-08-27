package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LogAlteracaoAuxRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoAuxResponseDTO;
import com.br.pruma.application.mapper.LogAlteracaoAuxMapper;
import com.br.pruma.core.domain.LogAlteracao;
import com.br.pruma.core.domain.LogAlteracaoAux;
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


    /**
     * Cria um novo LogAlteracaoAux vinculado a um LogAlteracao existente.
     */
    public LogAlteracaoAuxResponseDTO create(LogAlteracaoAuxRequestDTO dto) {
        LogAlteracao log = logRepo.findById(dto.getLogId())
                .orElseThrow(() ->
                        new EntityNotFoundException("LogAlteracao não encontrado: " + dto.getLogId())
                );

        // mapear corpo e stub de log via mapper, depois substituir stub pelo managed entity
        LogAlteracaoAux entity = mapper.toEntity(dto);
        entity.setLog(log);

        LogAlteracaoAux saved = auxRepo.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Busca um registro auxiliar por seu ID (igual ao ID do LogAlteracao).
     */
    @Transactional(readOnly = true)
    public LogAlteracaoAuxResponseDTO getById(Integer id) {
        LogAlteracaoAux entity = auxRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("LogAlteracaoAux não encontrado: " + id)
                );
        return mapper.toResponse(entity);
    }

    /**
     * Lista todos os registros auxiliares de log.
     */
    @Transactional(readOnly = true)
    public List<LogAlteracaoAuxResponseDTO> listAll() {
        return auxRepo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os registros auxiliares com um determinado tipo de alteração.
     */
    @Transactional(readOnly = true)
    public List<LogAlteracaoAuxResponseDTO> listByTipoAlteracao(String tipoAlteracao) {
        return auxRepo.findByTipoAlteracao(tipoAlteracao).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente o tipoAlteracao de um LogAlteracaoAux existente.
     */
    public LogAlteracaoAuxResponseDTO update(Integer id, LogAlteracaoAuxRequestDTO dto) {
        LogAlteracaoAux entity = auxRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("LogAlteracaoAux não encontrado: " + id)
                );

        mapper.updateFromDto(dto, entity);
        LogAlteracaoAux updated = auxRepo.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Remove um LogAlteracaoAux pelo seu ID.
     */
    public void delete(Integer id) {
        if (!auxRepo.existsById(id)) {
            throw new EntityNotFoundException("LogAlteracaoAux não encontrado: " + id);
        }
        auxRepo.deleteById(id);
    }

}
