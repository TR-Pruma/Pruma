package com.br.pruma.core.repository;

import com.br.pruma.core.domain.MensagemInstantanea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemInstantaneaRepository extends JpaRepository<MensagemInstantanea, Integer> {

    // Cliente.cpf é String (11 dígitos numéricos) — mesmo padrão de ProfissionalDeBase
    List<MensagemInstantanea> findAllByCliente_Cpf(String clienteCpf);

    List<MensagemInstantanea> findAllByTipoUsuario_Id(Integer tipoUsuarioId);

    List<MensagemInstantanea> findAllByDestinatarioId(String destinatarioId);
}
