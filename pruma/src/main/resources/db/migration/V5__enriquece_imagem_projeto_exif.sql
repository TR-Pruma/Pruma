-- V5: Adiciona metadados EXIF e hash de integridade em imagem_projeto

ALTER TABLE imagem_projeto
    ADD COLUMN IF NOT EXISTS exif_latitude   DOUBLE PRECISION,
    ADD COLUMN IF NOT EXISTS exif_longitude  DOUBLE PRECISION,
    ADD COLUMN IF NOT EXISTS exif_timestamp  TIMESTAMP,
    ADD COLUMN IF NOT EXISTS exif_manipulada BOOLEAN,
    ADD COLUMN IF NOT EXISTS hash_sha256     VARCHAR(64);

CREATE INDEX IF NOT EXISTS idx_imagem_exif_invalida ON imagem_projeto(exif_manipulada);
