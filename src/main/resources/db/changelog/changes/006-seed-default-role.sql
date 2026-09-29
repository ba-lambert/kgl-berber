--liquibase formatted sql
--changeset ba:7
INSERT INTO role (id, role, created_at, updated_at)
VALUES ('11111111-1111-1111-1111-111111111111', 'CUSTOMER', now(), now());
