package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Usuario;
import com.br.pruma.core.domain.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    Optional<Usuario> findByCpfAndTipo(String cpf, TipoUsuario tipo);
}

