-- =============================================================================
-- V37: CORRECAO DE TIPOS INT -> BIGINT
-- documento_id precisa ser BIGINT (Documento.java usa Long como PK).
--
-- MySQL nao permite MODIFY COLUMN em uma coluna que e alvo ou origem de FK
-- enquanto a coluna referenciada tem tipo diferente. A solucao correta e:
--   1. Dropar todas as FKs que referenciam documento_id
--   2. Fazer o MODIFY COLUMN nas duas pontas (PK e FKs)
--   3. Recriar as FKs
-- =============================================================================

-- 1) Dropar FKs que envolvem documento_id
ALTER TABLE assinatura_digital DROP FOREIGN KEY IF EXISTS fk_assinatura_documento;
ALTER TABLE anexo               DROP FOREIGN KEY IF EXISTS fk_anexo_documento;

-- Se o nome exato da FK for diferente, os comandos abaixo cobrem aliases comuns
ALTER TABLE assinatura_digital DROP FOREIGN KEY IF EXISTS fk_assinatura_digital_documento;
ALTER TABLE assinatura_digital DROP FOREIGN KEY IF EXISTS assinatura_digital_ibfk_1;
ALTER TABLE assinatura_digital DROP FOREIGN KEY IF EXISTS assinatura_digital_ibfk_2;
ALTER TABLE anexo               DROP FOREIGN KEY IF EXISTS anexo_ibfk_1;
ALTER TABLE anexo               DROP FOREIGN KEY IF EXISTS anexo_ibfk_2;

-- 2) Alterar a PK em documento
ALTER TABLE documento
  MODIFY COLUMN documento_id BIGINT NOT NULL AUTO_INCREMENT;

-- 3) Alterar FKs nas tabelas filhas para BIGINT
ALTER TABLE assinatura_digital
  MODIFY COLUMN documento_id BIGINT NOT NULL;

ALTER TABLE anexo
  MODIFY COLUMN documento_id BIGINT;

-- 4) Recriar as FKs
ALTER TABLE assinatura_digital
  ADD CONSTRAINT fk_assinatura_documento
    FOREIGN KEY (documento_id) REFERENCES documento(documento_id);

ALTER TABLE anexo
  ADD CONSTRAINT fk_anexo_documento
    FOREIGN KEY (documento_id) REFERENCES documento(documento_id);

-- 5) apadrinhamento_rede: garante BIGINT (sem FK externa, ALTER simples)
ALTER TABLE apadrinhamento_rede
  MODIFY COLUMN apadrinhamento_id BIGINT NOT NULL AUTO_INCREMENT;
