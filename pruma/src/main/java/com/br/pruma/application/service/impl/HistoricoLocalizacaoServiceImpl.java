package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.HistoricoLocalizacaoRequestDTO;
import com.br.pruma.application.dto.response.HistoricoLocalizacaoResponseDTO;
import com.br.pruma.application.mapper.HistoricoLocalizacaoMapper;
import com.br.pruma.application.service.HistoricoLocalizacaoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.HistoricoLocalizacao;
import com.br.pruma.core.domain.ProfissionalDeBase;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.HistoricoLocalizacaoRepository;
import com.br.pruma.core.repository.ProfissionalDeBaseRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoricoLocalizacaoServiceImpl implements HistoricoLocalizacaoService {

    private final HistoricoLocalizacaoRepository repository;
    private final ProfissionalDeBaseRepository profissionalRepository;
    private final ProjetoRepository projetoRepository;
    private final HistoricoLocalizacaoMapper mapper;

    @Override
    public HistoricoLocalizacaoResponseDTO salvar(HistoricoLocalizacaoRequestDTO dto) {
        String cpfStr = String.valueOf(dto.profissionalCpf());

        ProfissionalDeBase profissional = profissionalRepository
                .findByCpf(cpfStr)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Profissional com CPF " + cpfStr + " não encontrado."));

        Projeto projeto = projetoRepository.findById(dto.projetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Projeto com ID " + dto.projetoId() + " não encontrado."));

        HistoricoLocalizacao entity = mapper.toEntity(dto, profissional, projeto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoricoLocalizacaoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HistoricoLocalizacaoResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "HistoricoLocalizacao com ID " + id + " não encontrado."));
    }

    @Override
    public void deletar(Integer id) {
        HistoricoLocalizacao entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "HistoricoLocalizacao com ID " + id + " não encontrado."));
        entity.setAtivo(false);
        repository.save(entity);
    }
}
