--liquibase formatted sql
--changeset ba:9
CREATE TABLE invitations(
    id UUID PRIMARY KEY,
    email VARCHAR(200) NOT NULL,
    role_id UUID NOT NULL REFERENCES role(id),
    token VARCHAR(255) UNIQUE NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    accepted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
