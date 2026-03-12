CREATE TABLE category
(
    id             UUID        NOT NULL DEFAULT uuid_generate_v4(),
    tenant_id      UUID        NOT NULL,
    parent_id      UUID,
    category_name  VARCHAR(255),
    color_hex      VARCHAR(255),
    icon_code      VARCHAR(255),
    category_type  VARCHAR(30) NOT NULL,
    spending_limit DOUBLE PRECISION,
    create_at      TIMESTAMP WITHOUT TIME ZONE,
    update_at      TIMESTAMP WITHOUT TIME ZONE,

    -- Constraints
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_id)
        REFERENCES category (id) ON DELETE SET NULL
);

-- 2. Criação de Índices para Performance e Multi-tenancy
-- O tenant_id é crucial para performance em consultas filtradas por cliente
CREATE INDEX idx_category_tenant ON category (tenant_id);

-- Índice para facilitar a busca de subcategorias
CREATE INDEX idx_category_parent ON category (parent_id);