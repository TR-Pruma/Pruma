package com.br.pruma.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedFilter", condition = "ativo = :isDeleted")
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
