-- Garante que a extensão para UUID esteja disponível
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. Criação da Tabela Security Token
CREATE TABLE security_token
(
    id            UUID NOT NULL DEFAULT uuid_generate_v4(),
    user_id       UUID NOT NULL,
    tenant_id     UUID NOT NULL,
    token_id      VARCHAR(255),
    refresh_token VARCHAR(255),
    create_at     TIMESTAMP WITHOUT TIME ZONE,
    update_at     TIMESTAMP WITHOUT TIME ZONE,
    expired_at    TIMESTAMP WITHOUT TIME ZONE,
    revoke_at     TIMESTAMP WITHOUT TIME ZONE,
    last_used_at  TIMESTAMP WITHOUT TIME ZONE,

    -- Constraints
    CONSTRAINT pk_security_token PRIMARY KEY (id),
    CONSTRAINT fk_token_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- 2. Índices Estratégicos para Segurança e Performance
-- Busca rápida pelo token durante o processo de refresh
CREATE INDEX idx_token_refresh ON security_token (refresh_token);

-- Busca rápida para identificar tokens de um usuário específico
CREATE INDEX idx_token_user ON security_token (user_id);

-- Otimização para Multi-tenancy
CREATE INDEX idx_token_tenant ON security_token (tenant_id);

-- Índice para auxiliar jobs de limpeza (cleanup) de tokens expirados
CREATE INDEX idx_token_expired_at ON security_token (expired_at);