-- Garante que a extensão para UUID esteja disponível
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. Criação da Tabela Financial Transaction
CREATE TABLE IF NOT EXISTS financial_transaction
(
    id                        UUID           NOT NULL DEFAULT uuid_generate_v4(),
    tenant_id                 UUID           NOT NULL,
    account_id                UUID           NOT NULL,
    category_id               UUID,
    description               VARCHAR(255),
    amount                    DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    transaction_type          VARCHAR(50)    NOT NULL, -- Ex: INCOME, EXPENSE
    category_transaction_type VARCHAR(50),             -- Ex: FIXED, VARIABLE
    due_date                  DATE,
    payment_at                DATE,
    processed                 BOOLEAN        NOT NULL DEFAULT FALSE,
    processed_at              TIMESTAMP WITHOUT TIME ZONE,
    create_at                 TIMESTAMP WITHOUT TIME ZONE,
    update_at                 TIMESTAMP WITHOUT TIME ZONE,

    -- Constraints de integridade
    CONSTRAINT pk_financial_transaction PRIMARY KEY (id)

--     CONSTRAINT fk_transaction_account FOREIGN KEY (account_id)
--         REFERENCES account (id) ON DELETE CASCADE,
--
--     CONSTRAINT fk_transaction_category FOREIGN KEY (category_id)
--         REFERENCES category (id) ON DELETE SET NULL
);

-- 2. Índices Estratégicos para Performance
-- Essencial para isolamento de dados (Multi-tenancy)
CREATE INDEX IF NOT EXISTS idx_transaction_tenant ON financial_transaction (tenant_id);

-- Essencial para relatórios financeiros e extratos por conta
CREATE INDEX IF NOT EXISTS idx_transaction_account ON financial_transaction (account_id);
CREATE INDEX IF NOT EXISTS idx_transaction_due_date ON financial_transaction (due_date);

-- Índice composto para buscas de transações pendentes/processadas por data
CREATE INDEX IF NOT EXISTS idx_transaction_processed_flow ON financial_transaction (processed, due_date);