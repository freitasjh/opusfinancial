CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE tenant
(
    id        UUID DEFAULT uuid_generate_v4(),
    name      VARCHAR(255) NOT NULL,
    owner_id  UUID         NOT NULL,
    create_at TIMESTAMP    NOT NULL,
    update_at TIMESTAMP    NOT NULL,
    CONSTRAINT pk_tenant PRIMARY KEY (id)
);