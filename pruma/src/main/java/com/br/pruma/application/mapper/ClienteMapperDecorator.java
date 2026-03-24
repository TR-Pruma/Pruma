package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Endereco;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public abstract class ClienteMapperDecorator implements ClienteMapper {
    private final PasswordEncoder passwordEncoder;
    private final ClienteMapper delegate;

    public ClienteMapperDecorator(PasswordEncoder passwordEncoder, ClienteMapper delegate) {
        this.passwordEncoder = passwordEncoder;
        this.delegate = delegate;
    }

    @Override
    public Cliente toEntity(ClienteRequestDTO dto, Endereco endereco) {
        Cliente entity = delegate.toEntity(dto, endereco);
        // Criptografa a senha sempre na criação
        if (dto != null && notBlank(dto.senha())) {
            entity.setSenha(passwordEncoder.encode(dto.senha()));
        }

        // Garante regra padrão de negócio
        if (entity.getAtivo() == null) {
            entity.setAtivo(true);
        }
        return entity;
    }

    @Override
    public void updateEntity(Cliente entity, ClienteRequestDTO dto, Endereco endereco) {
        delegate.updateEntity(entity, dto, endereco);
        // Só reencode se senha informada (evita sobrescrever com vazio)
        if (dto != null && notBlank(dto.senha())) {
            entity.setSenha(passwordEncoder.encode(dto.senha()));
        }

    }

    private boolean notBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }


}
