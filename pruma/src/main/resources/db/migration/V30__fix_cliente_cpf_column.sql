-- V30: reconstroi tabela cliente alinhada com Cliente.java
-- O V1 criou a tabela com estrutura desatualizada (sem cliente_cpf, sem senha)
-- Banco limpo: drop + recreate e seguro

SET FOREIGN_KEY_CHECKS = 0;

-- Remove FKs dependentes antes do drop
ALTER TABLE projeto DROP FOREIGN KEY fk_projeto_cliente;

DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
    cliente_id  INT          NOT NULL AUTO_INCREMENT,
    cliente_cpf VARCHAR(11)  NOT NULL,
    nome        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    telefone    VARCHAR(20)  NOT NULL,
    endereco_id INT          NOT NULL,
    senha       VARCHAR(255) NOT NULL,
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)   NOT NULL DEFAULT 1,
    version     BIGINT       NOT NULL DEFAULT 0,
    PRIMARY KEY (cliente_id),
    CONSTRAINT uq_cliente_cpf   UNIQUE  (cliente_cpf),
    CONSTRAINT uq_cliente_email UNIQUE  (email),
    INDEX idx_cliente_cpf   (cliente_cpf),
    INDEX idx_cliente_email (email),
    CONSTRAINT fk_cliente_endereco FOREIGN KEY (endereco_id) REFERENCES endereco (endereco_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Restaura FK em projeto
ALTER TABLE projeto
    ADD CONSTRAINT fk_projeto_cliente
    FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id);

SET FOREIGN_KEY_CHECKS = 1;
