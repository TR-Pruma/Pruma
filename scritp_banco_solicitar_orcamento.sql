CREATE TABLE Usuario (
cpf INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
email VARCHAR(50) NOT NULL,
telefone int(15) NOT NULL,
senha VARCHAR(20) NOT NULL
);
CREATE TABLE Empresa (
cnpj INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
endereco VARCHAR(100) NOT NULL,
telefone VARCHAR(15) NOT NULL
);
CREATE TABLE Orcamento (
id_orcamento INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
descricao VARCHAR(200) NOT NULL,
localizacao VARCHAR(100) NOT NULL,
tamanho VARCHAR(20) NOT NULL,
fotos VARCHAR(100),
documentos VARCHAR(100),
horarios_trabalho VARCHAR(100),
num_max_empresas INT NOT NULL,
status VARCHAR(20) NOT NULL,
cpf INT NOT NULL,
cnpj INT,
FOREIGN KEY (cpf) REFERENCES Usuario(cpf),
FOREIGN KEY (cnpj) REFERENCES Empresa(cnpj)
);
CREATE TABLE Proposta (
id_proposta INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
valor DECIMAL(10,2) NOT NULL,
prazo_entrega INT NOT NULL,
descricao VARCHAR(200) NOT NULL,
status VARCHAR(20) NOT NULL,
id_orcamento INT NOT NULL,
cnpj INT NOT NULL,
FOREIGN KEY (id_orcamento) REFERENCES Orcamento(id_orcamento),
FOREIGN KEY (cnpj) REFERENCES Empresa(cnpj)
);
CREATE TABLE Categoria (
id_categoria INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(50) NOT NULL
);
CREATE TABLE EmpresaCategoria (
cnpj INT NOT NULL,
id_categoria INT NOT NULL,
PRIMARY KEY (cnpj, id_categoria),
FOREIGN KEY (cnpj) REFERENCES Empresa(cnpj),
FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);
CREATE TABLE Avaliacao (
id_avaliacao INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
nota INT NOT NULL,
comentario VARCHAR(200),
cpf INT NOT NULL,
cnpj INT NOT NULL,
FOREIGN KEY (cpf) REFERENCES Usuario(cpf),
FOREIGN KEY (cnpj) REFERENCES Empresa(cnpj)
);