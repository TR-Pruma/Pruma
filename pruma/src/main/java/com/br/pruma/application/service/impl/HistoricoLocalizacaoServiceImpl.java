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

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoLocalizacaoServiceImpl implements HistoricoLocalizacaoService {

    private final HistoricoLocalizacaoRepository repository;
    private final ProfissionalDeBaseRepository profissionalRepository;
    private final ProjetoRepository projetoRepository;
    private final HistoricoLocalizacaoMapper mapper;

    @Override
    public HistoricoLocalizacaoResponseDTO salvar(HistoricoLocalizacaoRequestDTO dto) {
        // dto.profissionalCpf() é Long — converte para String de 11 dígitos
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
    public List<HistoricoLocalizacaoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public HistoricoLocalizacaoResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "HistoricoLocalizacao com ID " + id + " não encontrado."));
    }

    @Override
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Não é possível deletar. HistoricoLocalizacao com ID " + id + " não existe.");
        }
        repository.deleteById(id);
    }
}
