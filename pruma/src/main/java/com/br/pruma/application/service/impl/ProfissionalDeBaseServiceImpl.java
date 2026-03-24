package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ProfissionalDeBaseRequestDTO;
import com.br.pruma.application.dto.response.ProfissionalDeBaseResponseDTO;
import com.br.pruma.application.service.ProfissionalDeBaseService;
import com.br.pruma.core.domain.ProfissionalDeBase;
import com.br.pruma.core.repository.ProfissionalDeBaseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfissionalDeBaseServiceImpl implements ProfissionalDeBaseService {

    private final ProfissionalDeBaseRepository profissionalDeBaseRepository;

    @Override
    @Transactional
    public ProfissionalDeBaseResponseDTO criar(ProfissionalDeBaseRequestDTO request) {
        ProfissionalDeBase entity = ProfissionalDeBase.builder()
                .nome(request.nome())
                .cpf(request.cpf())
                .email(request.email())
                .telefone(request.telefone())
                .especialidade(request.especialidade())
                .build();
        return toResponse(profissionalDeBaseRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfissionalDeBaseResponseDTO> listarTodos() {
        return profissionalDeBaseRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionalDeBaseResponseDTO buscarPorId(Integer id) {
        return profissionalDeBaseRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("ProfissionalDeBase não encontrado: " + id));
    }

    @Override
    @Transactional
    public ProfissionalDeBaseResponseDTO atualizar(Integer id, ProfissionalDeBaseRequestDTO request) {
        ProfissionalDeBase entity = profissionalDeBaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfissionalDeBase não encontrado: " + id));
        entity.setNome(request.nome());
        entity.setCpf(request.cpf());
        entity.setEmail(request.email());
        entity.setTelefone(request.telefone());
        entity.setEspecialidade(request.especialidade());
        return toResponse(profissionalDeBaseRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!profissionalDeBaseRepository.existsById(id)) {
            throw new EntityNotFoundException("ProfissionalDeBase não encontrado: " + id);
        }
        profissionalDeBaseRepository.deleteById(id);
    }

    private ProfissionalDeBaseResponseDTO toResponse(ProfissionalDeBase e) {
        return new ProfissionalDeBaseResponseDTO(
                e.getId(),
                e.getNome(),
                e.getCpf(),
                e.getEmail(),
                e.getTelefone(),
                e.getEspecialidade(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
