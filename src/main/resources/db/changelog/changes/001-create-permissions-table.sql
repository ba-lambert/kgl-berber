--liquibase formatted sql
--changeset ba:2
CREATE TABLE permissions(
    id UUID PRIMARY KEY,
    name VARCHAR(400) UNIQUE NOT NULL,
    description VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);