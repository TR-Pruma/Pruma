-- V30: corrige tabela cliente — coluna cliente_cpf ausente no V1
-- O Hibernate valida @Column(name="cliente_cpf") e @Index(name="idx_cliente_cpf")

ALTER TABLE cliente
    ADD COLUMN IF NOT EXISTS cliente_cpf VARCHAR(11) NOT NULL DEFAULT '' AFTER cliente_id;

-- Remove o DEFAULT vazio (coluna já existe, apenas estrutural)
ALTER TABLE cliente
    MODIFY COLUMN cliente_cpf VARCHAR(11) NOT NULL;

-- Garante unicidade exigida pela entidade
ALTER TABLE cliente
    ADD CONSTRAINT uq_cliente_cpf UNIQUE (cliente_cpf);

-- Índice nomeado exigido pelo @Index na entidade
CREATE INDEX IF NOT EXISTS idx_cliente_cpf  ON cliente (cliente_cpf);
CREATE INDEX IF NOT EXISTS idx_cliente_email ON cliente (email);
