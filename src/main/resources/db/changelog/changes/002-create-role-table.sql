--liquibase formatted sql
--changeset ba:3
CREATE TABLE role(
    id UUID PRIMARY KEY,
    role VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
