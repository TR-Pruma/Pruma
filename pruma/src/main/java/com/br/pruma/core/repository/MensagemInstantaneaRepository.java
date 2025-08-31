package com.br.pruma.core.repository;

import com.br.pruma.core.domain.MensagemInstantanea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemInstantaneaRepository extends JpaRepository<MensagemInstantanea, Integer> {

    /**
     * Lista todas as mensagens enviadas por um cliente específico.
     */
    List<MensagemInstantanea> findAllByCliente_Cpf(Long clienteCpf);

    /**
     * Lista todas as mensagens de um tipo de usuário específico.
     */
    List<MensagemInstantanea> findAllByTipoUsuario_Id(Integer tipoUsuarioId);

    /**
     * Lista todas as mensagens destinadas a um canal ou usuário específico.
     */
    List<MensagemInstantanea> findAllByDestinatarioId(String destinatarioId);
}

