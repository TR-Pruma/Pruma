-- V23: Converte documento_id de INT para BIGINT
-- Entidade Documento tem @Id Long, mas V1 criou o PK como INT.
-- MySQL exige que FK e PK referenciado sejam do mesmo tipo (erro 3780).
-- Ordem: 1) muda PK documento  2) muda FK columns  3) recria constraints

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Muda o PK de documento para BIGINT
--    (entidade Documento usa @Id Long)
ALTER TABLE documento
    MODIFY COLUMN documento_id BIGINT NOT NULL AUTO_INCREMENT;

-- 2. assinatura_digital: drop FK, muda coluna para BIGINT, recria FK
ALTER TABLE assinatura_digital
    DROP FOREIGN KEY fk_assin_documento;

ALTER TABLE assinatura_digital
    MODIFY COLUMN documento_id BIGINT NULL;

ALTER TABLE assinatura_digital
    ADD CONSTRAINT fk_assin_documento
        FOREIGN KEY (documento_id) REFERENCES documento (documento_id);

-- 3. anexo: drop FK, muda coluna para BIGINT, recria FK
ALTER TABLE anexo
    DROP FOREIGN KEY fk_anexo_documento;

ALTER TABLE anexo
    MODIFY COLUMN documento_id BIGINT NULL;

ALTER TABLE anexo
    ADD CONSTRAINT fk_anexo_documento
        FOREIGN KEY (documento_id) REFERENCES documento (documento_id);

SET FOREIGN_KEY_CHECKS = 1;
