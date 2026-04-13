package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.MensagemInstantaneaAux;
import com.br.pruma.core.enums.TipoMensagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de MensagemInstantaneaAux.
 * Define o contrato que a camada de aplicação usa sem depender
 * diretamente de JPA ou de qualquer tecnologia de persistência.
 */
public interface MensagemInstantaneaAuxRepositoryPort {

    /** Persiste ou atualiza um MensagemInstantaneaAux. */
    MensagemInstantaneaAux save(MensagemInstantaneaAux mensagemInstantaneaAux);

    /** Busca um MensagemInstantaneaAux pelo seu ID. */
    Optional<MensagemInstantaneaAux> findById(Integer id);

    /** Retorna todos os registros de MensagemInstantaneaAux. */
    List<MensagemInstantaneaAux> findAll();

    /** Retorna página de registros de MensagemInstantaneaAux. */
    Page<MensagemInstantaneaAux> findAll(Pageable pageable);

    /** Retorna todos os registros filtrados por tipo de mensagem. */
    List<MensagemInstantaneaAux> findByTipoMensagem(TipoMensagem tipoMensagem);

    /** Remove um MensagemInstantaneaAux pelo ID. */
    void deleteById(Integer id);

    /** Remove um MensagemInstantaneaAux pela entidade. */
    void delete(MensagemInstantaneaAux mensagemInstantaneaAux);

    /** Verifica se um MensagemInstantaneaAux existe pelo seu ID. */
    boolean existsById(Integer id);
}
