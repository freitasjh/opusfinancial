CREATE TABLE credit_card_transaction
(
    id        UUID NOT NULL DEFAULT uuid_generate_v4(),
    create_at TIMESTAMP WITHOUT TIME ZONE,
    update_at TIMESTAMP WITHOUT TIME ZONE,
    tenant_id        UUID NOT NULL,
    description      VARCHAR(255),
    amount           DECIMAL(19, 2),
    installments     INTEGER,
    credit_card_id   UUID NOT NULL,
    transaction_at   DATE,
    category_id      UUID,
    CONSTRAINT pk_credit_card_transaction PRIMARY KEY (id)
);