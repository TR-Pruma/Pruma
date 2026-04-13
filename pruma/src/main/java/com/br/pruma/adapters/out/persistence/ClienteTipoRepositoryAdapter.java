package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.ClienteTipo;
import com.br.pruma.core.repository.ClienteTipoRepository;
import com.br.pruma.core.repository.port.ClienteTipoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link ClienteTipoRepositoryPort}
 * delegando ao {@link ClienteTipoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class ClienteTipoRepositoryAdapter implements ClienteTipoRepositoryPort {

    private final ClienteTipoRepository clienteTipoRepository;

    @Override
    public ClienteTipo save(ClienteTipo clienteTipo) {
        return clienteTipoRepository.save(clienteTipo);
    }

    @Override
    public Optional<ClienteTipo> findById(Integer id) {
        return clienteTipoRepository.findById(id);
    }

    @Override
    public List<ClienteTipo> findByAtivoTrue() {
        return clienteTipoRepository.findByAtivoTrue();
    }

    @Override
    public Optional<ClienteTipo> findByIdAndAtivoTrue(Integer id) {
        return clienteTipoRepository.findByIdAndAtivoTrue(id);
    }

    @Override
    public List<ClienteTipo> findAll() {
        return clienteTipoRepository.findAll();
    }

    @Override
    public Page<ClienteTipo> findAll(Pageable pageable) {
        return clienteTipoRepository.findAll(pageable);
    }

    @Override
    public boolean existsByDescricaoClienteAndAtivoTrue(String descricaoCliente) {
        return clienteTipoRepository.existsByDescricaoClienteAndAtivoTrue(descricaoCliente);
    }

    @Override
    public void deleteById(Integer id) {
        clienteTipoRepository.deleteById(id);
    }

    @Override
    public void delete(ClienteTipo clienteTipo) {
        clienteTipoRepository.delete(clienteTipo);
    }

    @Override
    public boolean existsById(Integer id) {
        return clienteTipoRepository.existsById(id);
    }
}
