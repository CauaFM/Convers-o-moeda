-- Cria o banco de dados
CREATE DATABASE IF NOT EXISTS conversao_db;
USE conversao_db;

-- Tabela de moedas
CREATE TABLE IF NOT EXISTS moedas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL
);

-- Tabela de taxas
CREATE TABLE IF NOT EXISTS taxas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    moeda_id BIGINT NOT NULL,
    taxa_referencia DECIMAL(10, 2) NOT NULL,
    iof DECIMAL(10, 2) NOT NULL,
    spread DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (moeda_id) REFERENCES moedas(id)
);


-- Tabela de conversões
CREATE TABLE IF NOT EXISTS conversoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    moeda_origem_id BIGINT NOT NULL,
    moeda_destino_id BIGINT NOT NULL,
    valor DECIMAL(15, 2) NOT NULL,
    valor_convertido DECIMAL(15, 2) NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (moeda_origem_id) REFERENCES moedas(id),
    FOREIGN KEY (moeda_destino_id) REFERENCES moedas(id)
);

