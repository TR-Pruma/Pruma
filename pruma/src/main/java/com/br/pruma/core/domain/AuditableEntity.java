package com.br.pruma.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Superclasse de auditoria compartilhada por todas as entidades do domínio.
 * Fornece: createdAt, updatedAt, ativo (soft-delete) e version (optimistic lock).
 *
 * @SQLRestriction substitui o @Where deprecated do Hibernate 5.
 * O filtro garante que apenas registros ativos sejam retornados por padrão.
 */
@Getter
@Setter
@MappedSuperclass
@SQLRestriction("ativo = true")
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity implements Serializable {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Version
    @Column(name = "version")
    private Long version;
}
