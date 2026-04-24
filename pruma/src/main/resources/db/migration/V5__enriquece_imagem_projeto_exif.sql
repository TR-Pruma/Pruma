ALTER TABLE imagem_projeto
    ADD COLUMN exif_latitude   DOUBLE,
    ADD COLUMN exif_longitude  DOUBLE,
    ADD COLUMN exif_timestamp  TIMESTAMP,
    ADD COLUMN exif_manipulada BOOLEAN,
    ADD COLUMN hash_sha256     VARCHAR(64);

CREATE INDEX idx_imagem_exif_invalida ON imagem_projeto(exif_manipulada);