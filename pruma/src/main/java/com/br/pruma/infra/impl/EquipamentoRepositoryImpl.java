package com.br.pruma.infra.impl;

import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.enums.StatusEquipamento;
import com.br.pruma.infra.repository.EquipamentoRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class EquipamentoRepositoryImpl implements EquipamentoRepositoryCustom {

    private final EntityManager em;

    @Override
    public Page<Equipamento> searchIncludingInativos(
            String nomeLike,
            StatusEquipamento status,
            Boolean ativo,
            LocalDateTime criadoDe,
            LocalDateTime criadoAte,
            Pageable pageable
    ) {
        // Monta SQL nativo para bypass do @Where e permitir incluir inativos
        StringBuilder sql = new StringBuilder("""
            SELECT e.*
            FROM equipamento e
            WHERE 1=1
        """);

        StringBuilder sqlCount = new StringBuilder("""
            SELECT COUNT(1)
            FROM equipamento e
            WHERE 1=1
        """);

        Map<String, Object> params = new HashMap<>();

        if (StringUtils.hasText(nomeLike)) {
            sql.append(" AND LOWER(e.nome) LIKE :nome ");
            sqlCount.append(" AND LOWER(e.nome) LIKE :nome ");
            params.put("nome", "%" + nomeLike.toLowerCase(Locale.ROOT) + "%");
        }

        if (status != null) {
            sql.append(" AND e.status = :status ");
            sqlCount.append(" AND e.status = :status ");
            params.put("status", status.name());
        }

        if (ativo != null) {
            sql.append(" AND e.ativo = :ativo ");
            sqlCount.append(" AND e.ativo = :ativo ");
            params.put("ativo", ativo);
        }

        if (criadoDe != null) {
            sql.append(" AND e.data_criacao >= :criadoDe ");
            sqlCount.append(" AND e.data_criacao >= :criadoDe ");
            params.put("criadoDe", criadoDe);
        }

        if (criadoAte != null) {
            sql.append(" AND e.data_criacao <= :criadoAte ");
            sqlCount.append(" AND e.data_criacao <= :criadoAte ");
            params.put("criadoAte", criadoAte);
        }

        // Ordenação segura (whitelist de colunas)
        sql.append(buildOrderBy(pageable));

        // Query de dados
        Query dataQuery = em.createNativeQuery(sql.toString(), Equipamento.class);
        // Ajuste para Hibernate (evita warnings de retorno)
        if (dataQuery.unwrap(NativeQuery.class) != null) {
            dataQuery.unwrap(NativeQuery.class).addSynchronizedEntityClass(Equipamento.class);
        }
        params.forEach(dataQuery::setParameter);

        // Paginação
        if (pageable != null) {
            dataQuery.setFirstResult((int) pageable.getOffset());
            dataQuery.setMaxResults(pageable.getPageSize());
        }

        @SuppressWarnings("unchecked")
        List<Equipamento> content = dataQuery.getResultList();

        // Query de contagem
        Query countQuery = em.createNativeQuery(sqlCount.toString());
        params.forEach(countQuery::setParameter);
        long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    @Transactional
    public int softDeleteManyByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return 0;
        String jpql = "UPDATE Equipamento e SET e.ativo = false WHERE e.id IN :ids";
        return em.createQuery(jpql).setParameter("ids", ids).executeUpdate();
    }

    @Override
    @Transactional
    public int reativarManyByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return 0;
        String jpql = "UPDATE Equipamento e SET e.ativo = true WHERE e.id IN :ids";
        return em.createQuery(jpql).setParameter("ids", ids).executeUpdate();
    }

    private String buildOrderBy(Pageable pageable) {
        if (pageable == null || pageable.getSort().isEmpty()) {
            return " ORDER BY e.equipamento_id DESC ";
        }
        // Mapa de propriedades -> colunas; evita injection e erros de nome
        Map<String, String> colunaPorProp = Map.of(
                "id", "e.equipamento_id",
                "nome", "e.nome",
                "status", "e.status",
                "dataCriacao", "e.data_criacao",
                "dataAtualizacao", "e.data_atualizacao"
        );

        StringBuilder order = new StringBuilder(" ORDER BY ");
        pageable.getSort().forEach(orderItem -> {
            String prop = orderItem.getProperty();
            String coluna = colunaPorProp.getOrDefault(prop, "e.equipamento_id");
            order.append(coluna).append(orderItem.isAscending() ? " ASC, " : " DESC, ");
        });
        // remove a última vírgula
        order.setLength(order.length() - 2);
        return order.toString();
    }
}

