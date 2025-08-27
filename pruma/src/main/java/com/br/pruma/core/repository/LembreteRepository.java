package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Lembrete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LembreteRepository extends JpaRepository<Lembrete, Integer> {

    /**
     * Retorna todos os lembretes de um cliente, ordenados por data/hora.
     */
    List<Lembrete> findByCliente_CpfOrderByDataHora(String clienteCpf);

    /**
     * Retorna todos os lembretes de um determinado tipo de usuário.
     */
    List<Lembrete> findByTipoUsuario_IdOrderByDataHora(Integer tipoUsuarioId);
}

