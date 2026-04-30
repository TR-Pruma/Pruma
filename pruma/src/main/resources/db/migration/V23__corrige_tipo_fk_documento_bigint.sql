-- V23: Converte colunas documento_id de INT para BIGINT
-- A entidade Documento tem @Id Long, mas V1 criou as FKs como INT.
-- Hibernate validate falha com:
--   wrong column type [documento_id] in table [assinatura_digital]:
--   found [int], expecting [bigint]
--
-- FK_CHECKS off para permitir ALTER sem violar constraints temporariamente.

SET FOREIGN_KEY_CHECKS = 0;

-- assinatura_digital: remove FK, altera tipo, recria FK
ALTER TABLE assinatura_digital
    DROP FOREIGN KEY fk_assin_documento,
    MODIFY COLUMN documento_id BIGINT NULL,
    ADD CONSTRAINT fk_assin_documento
        FOREIGN KEY (documento_id) REFERENCES documento (documento_id);

-- anexo: remove FK, altera tipo, recria FK
ALTER TABLE anexo
    DROP FOREIGN KEY fk_anexo_documento,
    MODIFY COLUMN documento_id BIGINT NULL,
    ADD CONSTRAINT fk_anexo_documento
        FOREIGN KEY (documento_id) REFERENCES documento (documento_id);

SET FOREIGN_KEY_CHECKS = 1;
