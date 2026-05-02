-- V30: corrige tabela cliente -- coluna cliente_cpf ausente no V1
-- Banco limpo (drop total) -- sem risco de coluna duplicada

ALTER TABLE cliente
    ADD COLUMN cliente_cpf VARCHAR(11) NOT NULL DEFAULT '' AFTER cliente_id;

-- Remove o DEFAULT vazio estrutural
ALTER TABLE cliente
    MODIFY COLUMN cliente_cpf VARCHAR(11) NOT NULL;

-- Unicidade exigida por @Column(unique = true)
ALTER TABLE cliente
    ADD CONSTRAINT uq_cliente_cpf UNIQUE (cliente_cpf);

-- Indices nomeados exigidos pelo @Table(indexes = {...}) da entidade
CREATE INDEX idx_cliente_cpf   ON cliente (cliente_cpf);
CREATE INDEX idx_cliente_email ON cliente (email);
