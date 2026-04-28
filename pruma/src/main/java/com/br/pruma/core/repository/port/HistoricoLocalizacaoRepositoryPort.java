package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.HistoricoLocalizacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de HistoricoLocalizacao.
 */
public interface HistoricoLocalizacaoRepositoryPort {

    HistoricoLocalizacao save(HistoricoLocalizacao historicoLocalizacao);

    Optional<HistoricoLocalizacao> findById(Integer id);

    List<HistoricoLocalizacao> findAll();

    Page<HistoricoLocalizacao> findAll(Pageable pageable);

    void deleteById(Integer id);

    void delete(HistoricoLocalizacao historicoLocalizacao);

    boolean existsById(Integer id);
}
