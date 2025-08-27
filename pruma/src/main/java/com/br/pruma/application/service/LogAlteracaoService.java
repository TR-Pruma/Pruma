package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LogAlteracaoRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoResponseDTO;
import com.br.pruma.application.mapper.LogAlteracaoMapper;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.LogAlteracao;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.LogAlteracaoRepository;
import com.br.pruma.core.repository.TipoUsuarioRepository;
import com.br.pruma.infra.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LogAlteracaoService {
    private final LogAlteracaoRepository logRepo;
    private final ProjetoRepository projetoRepo;
    private final ClienteRepository clienteRepo;
    private final TipoUsuarioRepository tipoUsuarioRepo;
    private final LogAlteracaoMapper mapper;

    /**
     * Cria um novo registro de log de alteração.
     */
    public LogAlteracaoResponseDTO create(LogAlteracaoRequestDTO dto) {
        Projeto projeto = projetoRepo.findById(dto.getProjetoId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId())
                );
        Cliente cliente = clienteRepo.findById(Integer.valueOf(dto.getClienteCpf()))
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente não encontrado: " + dto.getClienteCpf())
                );
        TipoUsuario tipoUsuario = tipoUsuarioRepo.findById(dto.getTipoUsuarioId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Tipo de usuário não encontrado: " + dto.getTipoUsuarioId())
                );

        LogAlteracao entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        entity.setCliente(cliente);
        entity.setTipoUsuario(tipoUsuario);

        LogAlteracao saved = logRepo.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Busca um log de alteração por ID.
     */
    @Transactional(readOnly = true)
    public LogAlteracaoResponseDTO getById(Integer id) {
        LogAlteracao entity = logRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("LogAlteracao não encontrado: " + id)
                );
        return mapper.toResponse(entity);
    }

    /**
     * Retorna todos os registros de log, do mais recente para o mais antigo.
     */
    @Transactional(readOnly = true)
    public List<LogAlteracaoResponseDTO> listAll() {
        return logRepo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retorna todos os logs associados a um projeto específico.
     */
    @Transactional(readOnly = true)
    public List<LogAlteracaoResponseDTO> listByProjeto(Integer projetoId) {
        return logRepo.findByProjeto_IdOrderByDataHoraDesc(projetoId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retorna todos os logs associados a um CPF de cliente.
     */
    @Transactional(readOnly = true)
    public List<LogAlteracaoResponseDTO> listByCliente(String clienteCpf) {
        return logRepo.findByCliente_CpfOrderByDataHoraDesc(clienteCpf).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retorna todos os logs de um tipo de usuário específico.
     */
    @Transactional(readOnly = true)
    public List<LogAlteracaoResponseDTO> listByTipoUsuario(Integer tipoUsuarioId) {
        return logRepo.findByTipoUsuario_IdOrderByDataHoraDesc(tipoUsuarioId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Remove (hard delete) um log de alteração pelo ID.
     */
    public void delete(Integer id) {
        if (!logRepo.existsById(id)) {
            throw new EntityNotFoundException("LogAlteracao não encontrado: " + id);
        }
        logRepo.deleteById(id);
    }
}