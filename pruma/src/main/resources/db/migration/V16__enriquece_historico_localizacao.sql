-- V16: Enriquece historico_localizacao para RegistroGeolocalizacao completo
-- O estado atual (lat/lng/capturado_em) é insuficiente para validar
-- autenticidade de campo no eixo σ_tec do TFE.
-- precisao_metros: confiabilidade do sinal GPS
-- fonte: GPS | REDE | WIFI | DESCONHECIDO — detecta spoofing
-- interacao_id: vincula a geo à entrada MOR que a originou

ALTER TABLE historico_localizacao
    ADD COLUMN precisao_metros  DECIMAL(8,2)  NULL
        COMMENT 'Precisão do sinal GPS em metros'
        AFTER longitude,
    ADD COLUMN fonte            VARCHAR(20)   NULL
        COMMENT 'GPS | REDE | WIFI | DESCONHECIDO'
        AFTER precisao_metros,
    ADD COLUMN interacao_id     BIGINT        NULL
        COMMENT 'FK interacao_campo_mor — vincula geo à entrada MOR'
        AFTER fonte,
    ADD CONSTRAINT fk_historico_loc_interacao FOREIGN KEY (interacao_id)
        REFERENCES interacao_campo_mor (interacao_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    ADD INDEX idx_historico_loc_interacao     (interacao_id),
    ADD INDEX idx_historico_loc_obra_captura  (obra_id, capturado_em);
