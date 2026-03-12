-- Garante que a extensão para UUID esteja disponível
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. Criação da Tabela Users
CREATE TABLE users
(
    id         UUID         NOT NULL DEFAULT uuid_generate_v4(),
    tenant_id  UUID,
    email      VARCHAR(255) NOT NULL,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    document   VARCHAR(20),
    is_enabled BOOLEAN               DEFAULT TRUE,
    create_at  TIMESTAMP WITHOUT TIME ZONE,
    update_at  TIMESTAMP WITHOUT TIME ZONE,

    -- Constraints de Integridade
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT unique_users_email UNIQUE (email),
    CONSTRAINT unique_users_username UNIQUE (username)
);

-- 2. Índices para Performance e Segurança
-- O email e username são campos de busca frequente no login
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_username ON users (username);

-- O tenant_id é vital para o isolamento de dados no Monolito Modular
CREATE INDEX idx_users_tenant ON users (tenant_id);