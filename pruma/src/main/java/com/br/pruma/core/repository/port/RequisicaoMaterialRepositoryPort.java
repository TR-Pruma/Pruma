package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.RequisicaoMaterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de RequisicaoMaterial.
 * Define o contrato que a camada de aplicação usa sem depender
 * diretamente de JPA ou de qualquer tecnologia de persistência.
 */
public interface RequisicaoMaterialRepositoryPort {

    /** Persiste ou atualiza uma RequisicaoMaterial. */
    RequisicaoMaterial save(RequisicaoMaterial requisicaoMaterial);

    /** Busca uma RequisicaoMaterial pelo seu ID. */
    Optional<RequisicaoMaterial> findById(Integer id);

    /** Retorna todos os registros de RequisicaoMaterial. */
    List<RequisicaoMaterial> findAll();

    /** Retorna página de registros de RequisicaoMaterial. */
    Page<RequisicaoMaterial> findAll(Pageable pageable);

    /** Retorna requisições filtradas pelo ID da obra (projeto). */
    List<RequisicaoMaterial> findByProjetoId(Integer obraId);

    /** Retorna requisições filtradas pelo ID do material. */
    List<RequisicaoMaterial> findByMaterialId(Integer materialId);

    /** Remove uma RequisicaoMaterial pelo ID. */
    void deleteById(Integer id);

    /** Remove uma RequisicaoMaterial pela entidade. */
    void delete(RequisicaoMaterial requisicaoMaterial);

    /** Verifica se uma RequisicaoMaterial existe pelo seu ID. */
    boolean existsById(Integer id);
}
