package com.br.pruma.config;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class SoftDeleteConfig {

    private final EntityManager entityManager;

    public SoftDeleteConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void enableFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter")
              .setParameter("isDeleted", true);
    }

    public void disableFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.disableFilter("deletedFilter");
    }
}
