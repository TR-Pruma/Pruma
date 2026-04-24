package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.MensagemInstantanea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de MensagemInstantanea.
 * Define o contrato que a camada de aplicação usa sem depender
 * diretamente de JPA ou de qualquer tecnologia de persistência.
 */
public interface MensagemInstantaneaRepositoryPort {

    /** Persiste ou atualiza uma MensagemInstantanea. */
    MensagemInstantanea save(MensagemInstantanea mensagemInstantanea);

    /** Busca uma MensagemInstantanea pelo seu ID. */
    Optional<MensagemInstantanea> findById(Integer id);

    /** Retorna todos os registros de MensagemInstantanea. */
    List<MensagemInstantanea> findAll();

    /** Retorna página de registros de MensagemInstantanea. */
    Page<MensagemInstantanea> findAll(Pageable pageable);

    /** Retorna todas as mensagens de um cliente pelo CPF. */
    List<MensagemInstantanea> findAllByClienteCpf(String clienteCpf);

    /** Retorna todas as mensagens filtradas pelo tipo de usuário. */
    List<MensagemInstantanea> findAllByTipoUsuarioId(Integer tipoUsuarioId);

    /** Retorna todas as mensagens de um destinatário pelo ID. */
    List<MensagemInstantanea> findAllByDestinatarioId(String destinatarioId);

    /** Remove uma MensagemInstantanea pelo ID. */
    void deleteById(Integer id);

    /** Remove uma MensagemInstantanea pela entidade. */
    void delete(MensagemInstantanea mensagemInstantanea);

    /** Verifica se uma MensagemInstantanea existe pelo seu ID. */
    boolean existsById(Integer id);
}
