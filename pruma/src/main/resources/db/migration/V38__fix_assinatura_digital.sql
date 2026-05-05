-- =============================================================================
-- V38: Reconstrói assinatura_digital alinhando com AssinaturaDigital.java
--
-- Problema: V35 criou a PK como `assinatura_id` e a entidade Java espera
--           `assinatura_digital_id`. Além disso, falta a FK `usuario_id`
--           e a coluna `assinante` não existe na entidade (campo mapeado
--           via @ManyToOne para Usuario).
-- =============================================================================

-- 1. Remove dependentes que referenciam assinatura_digital (se houver)
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS assinatura_digital;

-- 2. Recria alinhada com AssinaturaDigital.java
CREATE TABLE assinatura_digital (
    assinatura_digital_id INT         NOT NULL AUTO_INCREMENT,
    usuario_id            INT         NOT NULL,
    documento_id          BIGINT      NOT NULL,
    hash                  VARCHAR(255),
    data_assinatura       DATETIME(6) NOT NULL,
    created_at            DATETIME(6) NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)  NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (assinatura_digital_id),
    CONSTRAINT fk_assinatura_usuario  FOREIGN KEY (usuario_id)
        REFERENCES usuario (usuario_id),
    CONSTRAINT fk_assinatura_documento FOREIGN KEY (documento_id)
        REFERENCES documento (documento_id),
    INDEX idx_assinatura_data_assinatura (data_assinatura),
    INDEX idx_assinatura_usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
