package com.br.pruma.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Habilita o suporte a auditoria JPA do Spring Data.
 * Necessario para que @CreationTimestamp e @UpdateTimestamp de {@link com.br.pruma.core.domain.AuditableEntity}
 * sejam processados corretamente pelo {@link org.springframework.data.jpa.domain.support.AuditingEntityListener}.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
