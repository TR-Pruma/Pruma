-- V12: trajetoria_laboral — portfólio auditável de obras/projetos do profissional
-- Representa a linha do tempo laboral verificável: cada obra, função e avaliação recebida.
-- Alimenta o eixo técnico do TFE (total_etapas_concluidas) e o campo
-- total_obras_finalizadas de profissional_de_base (adicionado em V17).

CREATE TABLE trajetoria_laboral (
    trajetoria_id           BIGINT          NOT NULL AUTO_INCREMENT,
    profissional_id         INT             NOT NULL,
    obra_id                 INT             NOT NULL,
    projeto_id              INT             NULL     COMMENT 'Opcional — projeto vinculado à obra',
    funcao_desempenhada     VARCHAR(100)    NOT NULL COMMENT 'Ex: pedreiro, pintor, eletricista',
    data_inicio             DATE            NOT NULL,
    data_fim                DATE            NULL     COMMENT 'NULL = obra ainda em andamento',
    obra_concluida          TINYINT(1)      NOT NULL DEFAULT 0,
    avaliacao_recebida      DECIMAL(3,1)    NULL     COMMENT 'Nota 0.0–10.0 dada pelo contratante',
    comentario_avaliacao    TEXT            NULL,
    created_at              DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at              DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    ativo                   TINYINT(1)      NOT NULL DEFAULT 1,
    version                 BIGINT          NOT NULL DEFAULT 0,
    PRIMARY KEY (trajetoria_id),
    CONSTRAINT fk_trajetoria_profissional FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_trajetoria_obra FOREIGN KEY (obra_id)
        REFERENCES obra (obra_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_trajetoria_projeto FOREIGN KEY (projeto_id)
        REFERENCES projeto (projeto_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_trajetoria_profissional          (profissional_id),
    INDEX idx_trajetoria_obra                  (obra_id),
    INDEX idx_trajetoria_portfolio             (profissional_id, obra_concluida, data_fim)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
