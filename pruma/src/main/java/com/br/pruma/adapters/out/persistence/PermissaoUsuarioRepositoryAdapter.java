package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.PermissaoUsuario;
import com.br.pruma.core.repository.PermissaoUsuarioRepository;
import com.br.pruma.core.repository.port.PermissaoUsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link PermissaoUsuarioRepositoryPort}
 * delegando ao {@link PermissaoUsuarioRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class PermissaoUsuarioRepositoryAdapter implements PermissaoUsuarioRepositoryPort {

    private final PermissaoUsuarioRepository permissaoUsuarioRepository;

    @Override
    public PermissaoUsuario save(PermissaoUsuario permissaoUsuario) {
        return permissaoUsuarioRepository.save(permissaoUsuario);
    }

    @Override
    public Optional<PermissaoUsuario> findById(Long id) {
        return permissaoUsuarioRepository.findById(id);
    }

    @Override
    public List<PermissaoUsuario> findAll() {
        return permissaoUsuarioRepository.findAll();
    }

    @Override
    public Page<PermissaoUsuario> findAll(Pageable pageable) {
        return permissaoUsuarioRepository.findAll(pageable);
    }

    @Override
    public List<PermissaoUsuario> findByClienteCpf(String cpf) {
        return permissaoUsuarioRepository.findByCliente_Cpf(cpf);
    }

    @Override
    public List<PermissaoUsuario> findByTipoUsuarioId(Integer tipoUsuarioId) {
        return permissaoUsuarioRepository.findByTipoUsuario_Id(tipoUsuarioId);
    }

    @Override
    public Optional<PermissaoUsuario> findByClienteCpfAndPermissao(String cpf, String permissao) {
        return permissaoUsuarioRepository.findByCliente_CpfAndPermissao(cpf, permissao);
    }

    @Override
    public void deleteById(Long id) {
        permissaoUsuarioRepository.deleteById(id);
    }

    @Override
    public void delete(PermissaoUsuario permissaoUsuario) {
        permissaoUsuarioRepository.delete(permissaoUsuario);
    }

    @Override
    public boolean existsById(Long id) {
        return permissaoUsuarioRepository.existsById(id);
    }
}
