--liquibase formatted sql
--changeset ba:10
INSERT INTO role (id, role, created_at, updated_at)
VALUES ('22222222-2222-2222-2222-222222222222', 'ADMIN', now(), now());

INSERT INTO permissions (id, name, description, created_at, updated_at)
VALUES ('33333333-3333-3333-3333-333333333333', 'INVITE_USER', 'Allows inviting new users by email and role', now(), now());

INSERT INTO role_permissions (role_id, permission_id)
VALUES ('22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333');
