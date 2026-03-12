CREATE TABLE IF NOT EXISTS credit_card_installment
(
    id          UUID NOT NULL DEFAULT uuid_generate_v4(),
    create_at   TIMESTAMP WITHOUT TIME ZONE,
    update_at   TIMESTAMP WITHOUT TIME ZONE,
    description VARCHAR(255),
    amount      DECIMAL(19, 2),
    due_date    DATE,
    installment INTEGER,
    transaction_id UUID,

    CONSTRAINT pk_credit_card_installment PRIMARY KEY (id)
);