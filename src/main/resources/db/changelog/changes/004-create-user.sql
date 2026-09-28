--liquibase formatted sql
--changeset ba:5
CREATE TABLE users(
    id UUID PRIMARY KEY,
    username VARCHAR(200) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    usr_role_id UUID NOT NULL REFERENCES role(id),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);