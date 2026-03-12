CREATE TABLE credit_card_invoice
(
    id        UUID NOT NULL DEFAULT uuid_generate_v4(),
    create_at TIMESTAMP WITHOUT TIME ZONE,
    update_at TIMESTAMP WITHOUT TIME ZONE,
    tenant_id        UUID NOT NULL,
    credit_card_id   UUID NOT NULL,
    status_type      VARCHAR(255),
    date_close       DATE,
    due_date         DATE,
    date_paid        DATE,
    CONSTRAINT pk_credit_card_invoice PRIMARY KEY (id)
);