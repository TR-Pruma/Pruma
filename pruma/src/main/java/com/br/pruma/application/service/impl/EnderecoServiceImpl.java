package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.EnderecoRequestDTO;
import com.br.pruma.application.dto.response.EnderecoResponseDTO;
import com.br.pruma.application.service.EnderecoService;
import com.br.pruma.core.domain.Endereco;
import com.br.pruma.core.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    public EnderecoResponseDTO criar(EnderecoRequestDTO request) {
        Endereco entity = Endereco.builder()
                .cep(request.cep())
                .numero(request.numero())
                .complemento(request.complemento())
                .build();
        return toResponse(enderecoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> listarTodos() {
        return enderecoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarPorId(Integer id) {
        return enderecoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Endereco não encontrado: " + id));
    }

    @Override
    @Transactional
    public EnderecoResponseDTO atualizar(Integer id, EnderecoRequestDTO request) {
        Endereco entity = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereco não encontrado: " + id));
        entity.setCep(request.cep());
        entity.setNumero(request.numero());
        entity.setComplemento(request.complemento());
        return toResponse(enderecoRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!enderecoRepository.existsById(id)) {
            throw new EntityNotFoundException("Endereco não encontrado: " + id);
        }
        enderecoRepository.deleteById(id);
    }

    private EnderecoResponseDTO toResponse(Endereco e) {
        return new EnderecoResponseDTO(
                e.getId(),
                e.getCep(),
                e.getNumero(),
                e.getComplemento(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
