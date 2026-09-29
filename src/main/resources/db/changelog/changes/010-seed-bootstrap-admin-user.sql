--liquibase formatted sql
--changeset ba:11
-- bootstrap account: password is "ChangeMe123!" (bcrypt hash below), log in and change it immediately via /api/auth/reset-password
INSERT INTO users (id, username, password, email, usr_role_id, created_at, updated_at)
VALUES (
    '44444444-4444-4444-4444-444444444444',
    'admin',
    '$2a$10$rtUkeml64/MOzt82nHipMO/QcJf2MTw59AmVyzgoPIJOXEhacuc7O',
    'admin@kigalibarber.local',
    '22222222-2222-2222-2222-222222222222',
    now(),
    now()
);
