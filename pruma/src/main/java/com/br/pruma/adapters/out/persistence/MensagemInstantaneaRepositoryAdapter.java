package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.MensagemInstantanea;
import com.br.pruma.core.repository.MensagemInstantaneaRepository;
import com.br.pruma.core.repository.port.MensagemInstantaneaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída (Output Adapter) que implementa o {@link MensagemInstantaneaRepositoryPort}
 * delegando as operações ao {@link MensagemInstantaneaRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class MensagemInstantaneaRepositoryAdapter implements MensagemInstantaneaRepositoryPort {

    private final MensagemInstantaneaRepository mensagemInstantaneaRepository;

    @Override
    public MensagemInstantanea save(MensagemInstantanea mensagemInstantanea) {
        return mensagemInstantaneaRepository.save(mensagemInstantanea);
    }

    @Override
    public Optional<MensagemInstantanea> findById(Integer id) {
        return mensagemInstantaneaRepository.findById(id);
    }

    @Override
    public List<MensagemInstantanea> findAll() {
        return mensagemInstantaneaRepository.findAll();
    }

    @Override
    public Page<MensagemInstantanea> findAll(Pageable pageable) {
        return mensagemInstantaneaRepository.findAll(pageable);
    }

    @Override
    public List<MensagemInstantanea> findAllByClienteCpf(String clienteCpf) {
        return mensagemInstantaneaRepository.findAllByCliente_Cpf(clienteCpf);
    }

    @Override
    public List<MensagemInstantanea> findAllByTipoUsuarioId(Integer tipoUsuarioId) {
        return mensagemInstantaneaRepository.findAllByTipoUsuario_Id(tipoUsuarioId);
    }

    @Override
    public List<MensagemInstantanea> findAllByDestinatarioId(String destinatarioId) {
        return mensagemInstantaneaRepository.findAllByDestinatarioId(destinatarioId);
    }

    @Override
    public void deleteById(Integer id) {
        mensagemInstantaneaRepository.deleteById(id);
    }

    @Override
    public void delete(MensagemInstantanea mensagemInstantanea) {
        mensagemInstantaneaRepository.delete(mensagemInstantanea);
    }

    @Override
    public boolean existsById(Integer id) {
        return mensagemInstantaneaRepository.existsById(id);
    }
}
