CREATE TABLE credit_card
(
    id               UUID NOT NULL  DEFAULT uuid_generate_v4(),
    create_at        TIMESTAMP WITHOUT TIME ZONE,
    update_at        TIMESTAMP WITHOUT TIME ZONE,
    tenant_id        UUID NOT NULL,
    name             VARCHAR(255),
    number           VARCHAR(255),
    cvv              VARCHAR(255),
    name_credit_card VARCHAR(255),
    total_limit      DECIMAL(19, 2) DEFAULT 0.0,
    available_limit  DECIMAL(19, 2) DEFAULT 0.0,
    due_date         VARCHAR(255),
    closing_date     VARCHAR(255),
    brand            VARCHAR(255),
    status           VARCHAR(255),
    account_id       UUID,

    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_credit_card_tenant ON credit_card (tenant_id);
CREATE INDEX IF NOT EXISTS idx_credit_card_tenant_account ON credit_card (account_id, tenant_id);