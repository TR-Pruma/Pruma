-- V17: Completa profissional_de_base como entidade ℒ de primeira classe
-- do trabalhador informal, conforme definição do documento técnico.
-- ATENÇÃO: score_atual_tfe_id referencia score_tfe criada em V10.
-- Depende de: V10 (score_tfe)

ALTER TABLE profissional_de_base
    ADD COLUMN nis_trabalhador         VARCHAR(11)  NULL
        COMMENT 'Número de Identificação Social (PIS/NIS/PASEP)'
        AFTER cpf,
    ADD COLUMN situacao_formal         VARCHAR(20)  NOT NULL DEFAULT 'INFORMAL'
        COMMENT 'FORMAL | INFORMAL | MEI'
        AFTER nis_trabalhador,
    ADD COLUMN total_obras_finalizadas INT          NOT NULL DEFAULT 0
        AFTER situacao_formal,
    ADD COLUMN total_obras_ativas      INT          NOT NULL DEFAULT 0
        AFTER total_obras_finalizadas,
    ADD COLUMN score_atual_tfe_id      BIGINT       NULL
        COMMENT 'FK score_tfe — score vigente. NULL até o primeiro cálculo do engine.'
        AFTER total_obras_ativas,
    ADD COLUMN data_cadastro           DATE         NULL
        AFTER score_atual_tfe_id,
    ADD COLUMN origem_cadastro         VARCHAR(20)  NULL
        COMMENT 'WHATSAPP_MOR | APP | INDICACAO'
        AFTER data_cadastro,
    ADD CONSTRAINT fk_profissional_score_atual FOREIGN KEY (score_atual_tfe_id)
        REFERENCES score_tfe (score_tfe_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    ADD INDEX idx_profissional_situacao_formal  (situacao_formal),
    ADD INDEX idx_profissional_score_atual      (score_atual_tfe_id),
    ADD INDEX idx_profissional_origem_cadastro  (origem_cadastro);
