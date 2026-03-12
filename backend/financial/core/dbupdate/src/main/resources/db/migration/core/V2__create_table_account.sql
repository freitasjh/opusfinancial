-- Garante que a extensão para UUID esteja disponível
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. Criação da Tabela Account
CREATE TABLE IF NOT EXISTS account
(
    id             UUID NOT NULL    DEFAULT uuid_generate_v4(),
    tenant_id      UUID NOT NULL,
    bank_id        UUID,
    account_name   VARCHAR(255),
    account_type   VARCHAR(255),
    account_number VARCHAR(255),
    agency         VARCHAR(255),
    balance        DECIMAL(19, 2)   DEFAULT 0.0,
    create_at      TIMESTAMP WITHOUT TIME ZONE,
    update_at      TIMESTAMP WITHOUT TIME ZONE,

    -- Constraints
    CONSTRAINT pk_account PRIMARY KEY (id)
);

-- 2. Índices para Performance e Integridade
-- Filtros por inquilino são a operação mais comum (Multi-tenancy)
CREATE INDEX IF NOT EXISTS idx_account_tenant ON account (tenant_id);

-- Otimização para buscas por banco ou número da conta
CREATE INDEX IF NOT EXISTS idx_account_bank ON account (bank_id);
CREATE INDEX IF NOT EXISTS idx_account_number ON account (account_number);