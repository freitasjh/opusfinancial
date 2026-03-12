-- Extensão necessária para geração de UUID caso não esteja ativa
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. Criação da Tabela Bank
CREATE TABLE bank
(
    id        UUID NOT NULL DEFAULT uuid_generate_v4(),
    code      VARCHAR(255),
    create_at TIMESTAMP WITHOUT TIME ZONE,
    update_at TIMESTAMP WITHOUT TIME ZONE,
    name      VARCHAR(255),
    logo_url  VARCHAR(255),
    category  INT4,

    CONSTRAINT pk_bank PRIMARY KEY (id)
);

-- 2. Inserção de Dados Iniciais
INSERT INTO bank (code, name, logo_url)
VALUES ('001', 'Banco do Brasil', 'https://logosdownload.com/logo/banco-do-brasil-logo-512.png'),
       ('237', 'Bradesco', 'https://logosdownload.com/logo/bradesco-logo-512.png'),
       ('341', 'Itaú Unibanco', 'https://logosdownload.com/logo/itau-logo-512.png'),
       ('033', 'Santander Brasil', 'https://logosdownload.com/logo/santander-logo-512.png'),
       ('104', 'Caixa Econômica Federal', 'https://logosdownload.com/logo/caixa-logo-512.png'),
       ('260', 'Nubank', 'https://logosdownload.com/logo/nubank-logo-512.png'),
       ('077', 'Banco Inter', 'https://logosdownload.com/logo/banco-inter-logo-512.png'),
       ('336', 'C6 Bank', 'https://logosdownload.com/logo/c6-bank-logo-512.png'),
       ('290', 'PagBank', 'https://logosdownload.com/logo/pagseguro-logo-512.png'),
       ('380', 'PicPay', 'https://logosdownload.com/logo/picpay-logo-512.png'),
       ('756', 'Sicoob (Bancoob)', 'https://logosdownload.com/logo/sicoob-logo-512.png'),
       ('748', 'Sicredi', 'https://logosdownload.com/logo/sicredi-logo-512.png'),
       ('136', 'Unicred', 'https://logosdownload.com/logo/unicred-logo-512.png'),
       ('085', 'Ailos (Viacredi)', 'https://logosdownload.com/logo/ailos-logo-512.png'),
       ('114', 'Cresol', 'https://logosdownload.com/logo/cresol-logo-512.png'),
       ('JPM', 'JPMorgan Chase (EUA)', 'https://logosdownload.com/logo/jp-morgan-chase-logo-512.png'),
       ('ICBC', 'ICBC (China)', 'https://logosdownload.com/logo/icbc-logo-512.png'),
       ('BAC', 'Bank of America (EUA)', 'https://logosdownload.com/logo/bank-of-america-logo-512.png'),
       ('HSBC', 'HSBC (Reino Unido)', 'https://logosdownload.com/logo/hsbc-logo-512.png'),
       ('BNP', 'BNP Paribas (França)', 'https://logosdownload.com/logo/bnp-paribas-logo-512.png'),
       ('REV', 'Revolut (Global Digital)', 'https://logosdownload.com/logo/revolut-logo-512.png'),
       ('999', 'Outros', '');
