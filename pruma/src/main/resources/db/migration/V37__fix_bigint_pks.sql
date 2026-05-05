-- =============================================================================
-- V37: CORRECAO DE TIPOS INT -> BIGINT
-- Entidades Java que declaram a PK como Long exigem BIGINT no banco.
-- O V35 criou todas as PKs como INT, causando falha na validacao do Hibernate.
--
-- Entidades afetadas (PK = Long):
--   Documento           -> documento_id
--   ApadrinhamentoRede  -> apadrinhamento_id  (ja BIGINT no V36, incluido por seguranca)
--
-- Colunas de referencia (FK) que tambem precisam ser BIGINT:
--   assinatura_digital.documento_id  (FK -> documento.documento_id)
-- =============================================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ---------------------------------------------------------------------------
-- documento: PK Long -> BIGINT
-- Entidade: Documento.java  private Long id;
-- ---------------------------------------------------------------------------
ALTER TABLE documento
  MODIFY COLUMN documento_id BIGINT NOT NULL AUTO_INCREMENT;

-- ---------------------------------------------------------------------------
-- assinatura_digital: FK documento_id deve acompanhar a PK de documento
-- Entidade: AssinaturaDigital.java  @JoinColumn(name="documento_id")
-- Hibernate reclama: found [int], expecting [bigint]
-- ---------------------------------------------------------------------------
ALTER TABLE assinatura_digital
  MODIFY COLUMN documento_id BIGINT NOT NULL;

-- ---------------------------------------------------------------------------
-- apadrinhamento_rede: PK ja foi criada como BIGINT no V36,
-- mas garantimos aqui caso alguem rode o V37 isolado.
-- Entidade: ApadrinhamentoRede.java  private Long apadrinhamentoId;
-- ---------------------------------------------------------------------------
ALTER TABLE apadrinhamento_rede
  MODIFY COLUMN apadrinhamento_id BIGINT NOT NULL AUTO_INCREMENT;

SET FOREIGN_KEY_CHECKS = 1;
