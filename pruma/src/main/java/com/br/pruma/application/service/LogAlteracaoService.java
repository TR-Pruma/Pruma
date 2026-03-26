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
import com.br.pruma.core.repository.ProjetoRepository;
import com.br.pruma.core.repository.TipoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LogAlteracaoService {

    private final LogAlteracaoRepository logRepo;
    private final ProjetoRepository projetoRepo;
    private final ClienteRepository clienteRepo;
    private final TipoUsuarioRepository tipoUsuarioRepo;
    private final LogAlteracaoMapper mapper;

    public LogAlteracaoResponseDTO create(LogAlteracaoRequestDTO dto) {
        Projeto projeto = projetoRepo.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
        Cliente cliente = clienteRepo.findByCpf(dto.getClienteCpf())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + dto.getClienteCpf()));
        TipoUsuario tipoUsuario = tipoUsuarioRepo.findById(dto.getTipoUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("TipoUsuario não encontrado: " + dto.getTipoUsuarioId()));

        LogAlteracao entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        entity.setCliente(cliente);
        entity.setTipoUsuario(tipoUsuario);

        return mapper.toResponse(logRepo.save(entity));
    }

    @Transactional(readOnly = true)
    public LogAlteracaoResponseDTO getById(Integer id) {
        return mapper.toResponse(
                logRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("LogAlteracao não encontrado: " + id))
        );
    }

    @Transactional(readOnly = true)
    public Page<LogAlteracaoResponseDTO> listAll(Pageable pageable) {
        return logRepo.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<LogAlteracaoResponseDTO> listByProjeto(Integer projetoId, Pageable pageable) {
        return logRepo.findByProjeto_IdOrderByDataHoraDesc(projetoId, pageable)
                .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<LogAlteracaoResponseDTO> listByCliente(String clienteCpf, Pageable pageable) {
        return logRepo.findByCliente_CpfOrderByDataHoraDesc(clienteCpf, pageable)
                .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<LogAlteracaoResponseDTO> listByTipoUsuario(Integer tipoUsuarioId, Pageable pageable) {
        return logRepo.findByTipoUsuario_IdOrderByDataHoraDesc(tipoUsuarioId, pageable)
                .map(mapper::toResponse);
    }

    public void delete(Integer id) {
        if (!logRepo.existsById(id)) {
            throw new EntityNotFoundException("LogAlteracao não encontrado: " + id);
        }
        logRepo.deleteById(id);
    }
}
