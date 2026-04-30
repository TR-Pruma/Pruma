-- V20: Adiciona coluna ativo em apadrinhamento_rede
-- A entidade JPA foi atualizada com campo @Column(name="ativo")
-- mas a migration V7 nao incluia essa coluna.
-- Default TRUE preserva todos os registros existentes como ativos.

ALTER TABLE apadrinhamento_rede
    ADD COLUMN ativo TINYINT(1) NOT NULL DEFAULT 1;
