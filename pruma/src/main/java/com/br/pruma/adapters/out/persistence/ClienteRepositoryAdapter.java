package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.port.ClienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link ClienteRepositoryPort}
 * delegando ao {@link ClienteRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Optional<Cliente> findByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    @Override
    public boolean existsById(Integer id) {
        return clienteRepository.existsById(id);
    }
}
